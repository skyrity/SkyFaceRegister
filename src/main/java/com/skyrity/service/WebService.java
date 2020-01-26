package com.skyrity.service;

import com.skyrity.bean.Face_System;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ： macai@skyrity.com
 * @date ：2020/01/05 21:01
 * @description : ${description}
 * @path : com.skyrity.service.WebService
 * @modifiedBy ：
 */
public interface WebService {
    String login(HttpServletRequest request,String userName,String password) throws Base64DecodingException;

    String logout(HttpServletRequest request);

    String getfaces(HttpServletRequest request,int state,String fields,int pageNum,int pageSize);

    String approve(HttpServletRequest request,String ids,int isPass);

    String register(HttpServletRequest request, MultipartFile file);

    long editPassword(HttpServletRequest request,String password);

    boolean checkUser(String userName, String password);

    Face_System getSystem();
}
