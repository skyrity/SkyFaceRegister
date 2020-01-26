package com.skyrity.service;

import com.skyrity.bean.Face_System;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ： VULCAN
 * @date ：2020/01/16 13:05
 * @description : ${description}
 * @path : com.skyrity.service.FaceSystemService
 * @modifiedBy ：
 */
public interface FaceSystemService {

    Face_System getSystem();
    boolean checkUser(String userName, String password);
    long editPassword(String password);
}
