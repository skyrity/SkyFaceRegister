package com.skyrity.controller;

import com.skyrity.bean.Face_Register;
import com.skyrity.bean.Face_System;
import com.skyrity.service.WebService;
import com.skyrity.service.WxService;
import com.skyrity.utils.ComUtil;
import com.skyrity.utils.MD5Util;
import com.skyrity.utils.RetCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ： macai
 * @date ：2020/01/08 12:03
 * @description : 提供对外访问服务，所有请求返回都为JSON数据
 * @path : com.skyrity.controller.ServiceController
 * @modifiedBy ：
 */

@Controller
public class ServiceController {
    private static Logger logger = Logger.getLogger(ServiceController.class);
    @Autowired
    WxService wxService;
    @Autowired
    WebService webService;

    @RequestMapping("wxLogin.do")
    public void wxlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("表现层，wxlogin...");

        ComUtil.printWrite(response,wxService.login(request));
    }

    @RequestMapping("login.do")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("表现层，login...");
        String retStr;
        String userName = request.getParameter("userName");//用户名
        String password = request.getParameter("password");//密码
        //1.判断参数
        if (userName == null || password == null  ) {
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
            logger.info(retStr);
            ComUtil.printWrite(response, retStr);
            return;
        }
        ComUtil.printWrite(response, webService.login(request,userName,password));
    }
    @RequestMapping("logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("表现层，logout...");
        String retStr;
        String accessToken = request.getParameter("accessToken");//访问令牌
        String token= (String) request.getSession().getAttribute("accessToken");
        //1.参数判断
        if (accessToken == null ) {
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
            ComUtil.printWrite(response,retStr);
            return;
        }
        ComUtil.printWrite(response, webService.logout(request));
    }

    @RequestMapping(value="register.do",method = RequestMethod.POST)
    @ResponseBody
    public void register(HttpServletRequest request, HttpServletResponse response,@RequestParam("file") MultipartFile file) throws Exception {

        logger.debug("表现层，register...");
        String retStr;
        String accessToken = request.getParameter("accessToken");//访问令牌
        String name = request.getParameter("name");//用户名
        String telNo = request.getParameter("telNo");//用户手机号
        String session_key= (String) request.getSession().getAttribute("session_key");
        String token= (String) request.getSession().getAttribute("accessToken");
        String openId=(String) request.getSession().getAttribute("openId");

        //1.参数判断
        if (accessToken == null || name == null || telNo == null || file.isEmpty()) {
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
            ComUtil.printWrite(response,retStr);
            return;
        }
        //2.检查令牌
        if(token==null && session_key==null){
            retStr=  ComUtil.getResultTime(RetCode.RET_ERROR_LOGIN); //未登录
            ComUtil.printWrite(response,retStr);
            return;
        }

        if (token!=null && !token.equals(accessToken)){
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
            ComUtil.printWrite(response,retStr);
        }else if (session_key!=null && !session_key.equals(accessToken)){
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
            ComUtil.printWrite(response,retStr);
        }
        if(openId==null){
            openId="pclogin";
        }

        ComUtil.printWrite(response,webService.register(request,file));
    }



    @RequestMapping("getfaces.do")
    public void getfaces(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("表现层，getfaces...");
        String accessToken = request.getParameter("accessToken");//令牌
        String sState = request.getParameter("state");//状态
        String fields = request.getParameter("fields");//查询字段
        String sPageSize=request.getParameter("pageSize"); //页大小
        String sPageNum=request.getParameter("pageNum"); //页索引，第几页

        String session_key= (String) request.getSession().getAttribute("session_key");
        String token= (String) request.getSession().getAttribute("accessToken");

        int state= Face_Register.VALUE_ALL;
        if(sState != null ){
            try {
                state=Integer.parseInt(sState);
            }catch(Exception e){
                e.printStackTrace();
           }
        }
        int pageSize= 40;
        if(sPageSize != null ){
            try {
                pageSize=Integer.parseInt(sPageSize);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        int pageNum= 1;
        if(sPageNum != null ){
            try {
                pageNum=Integer.parseInt(sPageNum);
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        if(session_key==null && accessToken==null){
           ComUtil.printWrite(response,ComUtil.getResultTime(RetCode.RET_ERROR_LOGIN)); //未登录
        }else if(session_key!=null && session_key.equals(accessToken)) { // PC端登录
            ComUtil.printWrite(response, webService.getfaces(request,state,fields,pageNum,pageSize));
        }else if(token!=null && token.equals(accessToken)){ //微信端登陆
            ComUtil.printWrite(response, wxService.getfaces(request,state,fields,pageNum,pageSize));
        }else{
            ComUtil.printWrite(response,ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS)); //参数错误
        }
    }

    @RequestMapping("approve.do")
    public void approve(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("表现层，approve...");
        String retStr;
        String accessToken = request.getParameter("accessToken");//令牌
        String ids = request.getParameter("ids");
        String sIsPass = request.getParameter("isPass");
        String session_key= (String) request.getSession().getAttribute("session_key");
        String token= (String) request.getSession().getAttribute("accessToken");

        //1.判断参数
        if (accessToken == null || ids==null || sIsPass == null  ) {
             retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
             ComUtil.printWrite(response,retStr);
             return;
        }
        int isPass=0;
        try {
            isPass = Integer.parseInt(sIsPass);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(session_key==null && accessToken==null){
            ComUtil.printWrite(response,ComUtil.getResultTime(RetCode.RET_ERROR_LOGIN)); //未登录
        }else if(session_key!=null && session_key.equals(accessToken)) {
            ComUtil.printWrite(response, webService.approve(request,ids,isPass ));
        }else if(token!=null && token.equals(accessToken)){
            ComUtil.printWrite(response, webService.approve(request,ids,isPass ));
        }else {
            ComUtil.printWrite(response,ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS)); //参数错误
        }
    }

    @RequestMapping("editPassword.do")
    public void editPassword(HttpServletRequest request, HttpServletResponse response){

        logger.debug("表现层，editPassword...");
        String retStr;
        String accessToken = request.getParameter("accessToken");//令牌
        String oldPassword = request.getParameter("oldPassword");//旧密码
        String newPassword = request.getParameter("newPassword");//新密码
        Face_System faceSystem=webService.getSystem();

        if(accessToken==null || oldPassword==null || newPassword==null){
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS);
            ComUtil.printWrite(response,retStr);
            return;
        }
        String password=MD5Util.stringToMD5(oldPassword);
        if(!password.equals(faceSystem.getPassword())){
            retStr= ComUtil.getResultTime(RetCode.RET_ERROR_OLDPASSWORD);
            ComUtil.printWrite(response,retStr);
            return;
        }

        String session_key= (String) request.getSession().getAttribute("session_key");
        String token= (String) request.getSession().getAttribute("accessToken");

        if(session_key==null && accessToken==null){
            ComUtil.printWrite(response,ComUtil.getResultTime(RetCode.RET_ERROR_LOGIN)); //未登录
        }else if((session_key!=null && session_key.equals(accessToken)) ||
                (token!=null && token.equals(accessToken))) {
            password=MD5Util.stringToMD5(newPassword);
            long ret=webService.editPassword(request,password);
            if(ret>0){
                ComUtil.printWrite(response,ComUtil.getResultTime(RetCode.RET_SUCCESS));
            }else{
                ComUtil.printWrite(response,ComUtil.getResultTime(RetCode.RET_ERROR_EDITPASSWORD));//修改密码失败
            }
        }else{
            ComUtil.printWrite(response,ComUtil.getResultTime(RetCode.RET_ERROR_PARAMS)); //参数错误
        }


    }


    }
