package com.skyrity.service.impl;

import com.skyrity.bean.Face_Register;
import com.skyrity.bean.Face_System;
import com.skyrity.bean.Pager;
import com.skyrity.dao.FaceSystemDao;
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
 * @modifiedBy ：
 */
@Service("WebService")
public class WebServiceImpl implements com.skyrity.service.WebService{
    private static Logger logger = Logger.getLogger(WebServiceImpl.class);
    @Autowired
    FaceRegisterService faceRegisterService;
    @Autowired
    FaceSystemService faceSystemService;
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

        //3.查询数据库
        Pager<Face_Register> pager=faceRegisterService.getFields(state,fields,pageNum,pageSize);

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
        for(int i=0;i<sId.length;i++) {
            int id=Integer.parseInt(sId[i]);
            ret = faceRegisterService.approve(id, state);
            if(ret<=0){
                retStr= ComUtil.getResultTime(RetCode.RET_ERROR_APPROVE);
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
        String openId=(String) request.getSession().getAttribute("openId");

        if(openId==null){
            openId="pclogin";
        }

        //3.保存文件
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

        //4.检查该用户是否已经存在
        Face_Register faceRegister =faceRegisterService.getByTelephone(telNo);
        if(faceRegister!=null){
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_REGISTERED);
            logger.info(retStr);
            return retStr;
        }
        //5.保存数据库，增加人脸注册记录
        Face_System faceSystem=getSystem();
        faceRegister=new Face_Register();
        faceRegister.setName(name);
        faceRegister.setImgUrl(imgUrl);
        faceRegister.setOpenId(openId);
        faceRegister.setTelNo(telNo);
        faceRegister.setApplyTime(new Date());
        String sCardNo=telNo.substring(2);
        long cardNo=0;
        try {
            cardNo = Integer.parseInt(sCardNo);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_EXCEPTION);
            logger.info(retStr);
            return retStr;
        }
        faceRegister.setCardNo(cardNo);
        if(faceSystem.getState()==Face_System.STATE_NOAPPROVE) { //不审核
            faceRegister.setState(Face_Register.VALUE_PASS);
        }else{
            faceRegister.setState(Face_Register.VALUE_UNCONFIRMED);
        }

        long ret=faceRegisterService.add(faceRegister);
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
    public boolean checkUser(String userName, String password) {
        Face_System faceSystem=faceSystemService.getSystem();
        String passwordMd5= MD5Util.stringToMD5(password);
        if(userName.equals(faceSystem.getUserName()) && passwordMd5.equals(faceSystem.getPassword())){
            return true;
        }
        return false;
    }



}
