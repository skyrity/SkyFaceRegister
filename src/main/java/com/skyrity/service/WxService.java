package com.skyrity.service;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 20:18
 * @description : ${description}
 * @path : com.skyrity.service.WxService
 * @modifiedBy ：
 */
public interface WxService {

    String login(HttpServletRequest request) throws Base64DecodingException;

    public String getfaces(HttpServletRequest request,int state,String fields,int pageNum,int pageSize);
    //String wxRegister(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException;
}
