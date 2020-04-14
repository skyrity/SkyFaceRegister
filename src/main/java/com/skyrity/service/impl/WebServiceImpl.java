package com.skyrity.service.impl;

import com.skyrity.bean.Face_Project;
import com.skyrity.bean.Face_Register;
import com.skyrity.bean.Face_System;
import com.skyrity.bean.Pager;
import com.skyrity.service.FaceProjectService;
import com.skyrity.service.FaceRegisterService;
import com.skyrity.service.FaceSystemService;
import com.skyrity.utils.ComUtil;
import com.skyrity.utils.MD5Util;
import com.skyrity.utils.RetCode;
import com.skyrity.utils.TokenProccessor;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 21:01
 * @description : ${description}
 * @path : com.skyrity.service.impl.WebServiceImpl
 */
@Service("WebService")
public class WebServiceImpl implements com.skyrity.service.WebService{
    private static Logger logger = Logger.getLogger(WebServiceImpl.class);
    @Autowired
    private FaceRegisterService faceRegisterService;
    @Autowired
    private FaceSystemService faceSystemService;
    @Autowired
    private FaceProjectService faceProjectService;

    private final int SKYPROJECT=1;

    @Override
    public String login(HttpServletRequest request,String userName,String password) throws Base64DecodingException {
        String retStr;

        //2.检查用户
        if(!checkUser(userName,password)){
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_USERPASS);
            logger.info(retStr);
            return retStr;
        }
       String accessToken=TokenProccessor.getInstance().makeToken();
       request.getSession().setAttribute("accessToken",accessToken);
       request.getSession().setAttribute("userName",userName);
       request.getSession().setAttribute("projectId",SKYPROJECT);
       request.getSession().setMaxInactiveInterval(3600);

       retStr= RetCode.RET_SUCCESS.replace("RESULT_DATA","{\"accessToken\":\""+accessToken+"\"}").
                replace("RESULT_TIME",Long.toString(new Date().getTime()));
       logger.info(retStr);
       return retStr;
    }

    @Override
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("userName");
        request.getSession().removeAttribute("accessToken");
        String retStr= RetCode.RET_SUCCESS.replace("RESULT_DATA","{}").
                replace("RESULT_TIME",Long.toString(new Date().getTime()));
        logger.info(retStr);
        return retStr;

    }

    @Override
    public String getfaces(HttpServletRequest request,int state,String fields,int pageNum,int pageSize) {
        String retStr;
        String field1;
        StringBuilder in_Where=new StringBuilder("");
        if(fields!=null && !"".equals(fields)){
            field1="(name like '%"+fields+"%' or telNo like '%"+fields+"%') ";
            in_Where.append(field1);
        }

        if(state!=Face_Register.VALUE_ALL){
            if (in_Where.length()==0) {
                field1=" state=" + state;
            }else{
                field1=" and state=" + state;

            }
            in_Where.append(field1);
        }
        //3.查询数据库
        logger.info("getfaces(PC):in_Where="+in_Where);
        Pager<Face_Register> pager=faceRegisterService.getPager(in_Where.toString(),pageNum,pageSize);

        JSONObject jsonData= JSONObject.fromObject(pager);
        request.setAttribute("pager",pager);
         retStr= RetCode.RET_SUCCESS.replace("RESULT_DATA",jsonData.toString()).
                replace("RESULT_TIME",Long.toString(new Date().getTime()));
        logger.info(retStr);
        return retStr;
    }


    @Override
    public String approve(HttpServletRequest request,String ids,int isPass) {

        String retStr;
        //3.修改数据库
        String[] sId= ids.split(",");
        int state=Face_Register.VALUE_UNCONFIRMED;
        if(isPass==0){
           state=Face_Register.VALUE_NOPASS; //拒绝
        }else if(isPass==1){
           state=Face_Register.VALUE_PASS;//通过
        }else{
           state=Face_Register.VALUE_HANDLE;//已处理
        }
        long ret;
        for (String aSId : sId) {
            int id = Integer.parseInt(aSId);
            ret = faceRegisterService.approve(id, state);
            if (ret <= 0) {
                retStr = ComUtil.getResultTime(RetCode.RET_ERROR_APPROVE);
                logger.info(retStr);
                return retStr;
            }
        }

         retStr= RetCode.RET_SUCCESS.replace("RESULT_DATA","\"\"").
                replace("RESULT_TIME",Long.toString(new Date().getTime()));
        logger.info(retStr);
        return retStr;
    }

    @Override
    public String register(HttpServletRequest request, MultipartFile file) {
        String retStr;
        String name = request.getParameter("name");//用户名
        String telNo = request.getParameter("telNo");//用户手机号
        String sCardId = request.getParameter("cardId");//卡号
        String openId=(String) request.getSession().getAttribute("openId");
        int projectId=(int) request.getSession().getAttribute("projectId");

        if(openId==null){
            openId="pclogin";
        }


        //4.保存文件
        String storePath =request.getServletContext().getRealPath("/") + "upload";
        Random r = new Random();
        String fileName= name +"_" +telNo+ "_" + r.nextInt(1000) + ".jpg";

        File filePath = new File(storePath, fileName);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();// 如果目录不存在，则创建目录
        }

        try {
            file.transferTo(new File(storePath + File.separator + fileName));// 把文件写入目标文件地址
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
            logger.info(retStr);
            return retStr;
        }
        String imgUrl =request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+
                request.getContextPath()+"/upload/"+fileName;

        if(sCardId==null || "".equals(sCardId)){
            sCardId=telNo.substring(2);
        }
        int cardId=-3;
        try{
            cardId=Integer.parseInt(sCardId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
            logger.info(retStr);
            return retStr;
        }
        //5.保存数据库，增加人脸注册记录

        Face_System faceSystem=getSystem();

        Face_Register faceRegister=new Face_Register();
        faceRegister.setName(name);
        faceRegister.setImgUrl(imgUrl);
        faceRegister.setOpenId(openId);
        faceRegister.setTelNo(telNo);
        faceRegister.setCardNo(cardId);
        faceRegister.setProjectId(projectId);
        faceRegister.setApplyTime(new Date());
        faceRegister.setCardNo(cardId);
        if(faceSystem.getState()==Face_System.STATE_NOAPPROVE) { //不审核
            faceRegister.setState(Face_Register.VALUE_PASS);
        }else{
            faceRegister.setState(Face_Register.VALUE_UNCONFIRMED);
        }
        //检查该用户是否已经存在
        long ret;
        Face_Register faceRegister1 =faceRegisterService.getByTelephone(telNo);
        if(faceRegister1!=null){
            faceRegister.setId(faceRegister1.getId());
            ret=faceRegisterService.edit(faceRegister);
        }else{
            ret=faceRegisterService.add(faceRegister);
        }

        if(ret>0) {
            retStr= RetCode.RET_SUCCESS.replace("RESULT_DATA", "{\"imgUrl\":\"" + imgUrl+"\"}").
                    replace("RESULT_TIME", Long.toString(new Date().getTime()));
        }else{
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_EXCEPTION);

        }
        logger.info(retStr);
        return retStr;

    }

    @Override
    public long editPassword(HttpServletRequest request, String password) {
        return faceSystemService.editPassword(password);
    }


    @Override
    public Face_System getSystem() {
        return faceSystemService.getSystem();
    }

    @Override
    public long editProjectPass(HttpServletRequest request, int projectId,String password) {
        return faceProjectService.editProjectPass(projectId,password);
    }

    @Override
    public String pcLogin(HttpServletRequest request, String projectNo, String password) throws Base64DecodingException {
        String retStr;

        //2.检查用户
        if(!checkProjectUser(projectNo,password)){
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PROJECT_ACCOUNT);
            logger.info(retStr);
            return retStr;
        }
        Face_Project faceProject=faceProjectService.getByProjectNo(projectNo);
        String accessToken=TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("accessToken",accessToken);
        request.getSession().setAttribute("projectId",faceProject.getId());
        request.getSession().setMaxInactiveInterval(3600);

        retStr= RetCode.RET_SUCCESS.replace("RESULT_DATA","{\"accessToken\":\""+accessToken+"\"}").
                replace("RESULT_TIME",Long.toString(new Date().getTime()));
        logger.info(retStr);
        return retStr;
    }

    @Override
    public boolean checkUser(String userName, String password) {
        Face_System faceSystem=faceSystemService.getSystem();
        String passwordMd5= MD5Util.stringToMD5(password);
        return userName.equals(faceSystem.getUserName()) && passwordMd5.equals(faceSystem.getPassword());
    }

    @Override
    public boolean checkProjectUser(String projectNo, String password) {
        Face_Project faceProject=faceProjectService.getByProjectNo(projectNo);
        String passwordMd5= MD5Util.stringToMD5(password);
        return projectNo.equals(faceProject.getProjectNo()) && passwordMd5.equals(faceProject.getPassword());
    }

    @Override
    public Face_Project getProjectById(int projectId) {
        return faceProjectService.getById(projectId);
    }

}
