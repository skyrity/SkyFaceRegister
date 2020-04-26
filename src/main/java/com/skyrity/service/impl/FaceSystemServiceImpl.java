package com.skyrity.service.impl;

import com.skyrity.bean.Face_System;
import com.skyrity.dao.FaceSystemDao;
import com.skyrity.service.FaceSystemService;
import com.skyrity.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 19:50
 * @description : ${description}
 * @path : com.skyrity.service.impl.FaceSystemServiceImpl
 * @modifiedBy ：
 */
@Service("FaceSystemService")
public class FaceSystemServiceImpl implements FaceSystemService {

    @Autowired
    private
    FaceSystemDao faceSystemDao;

    @Override
    public Face_System getSystem() {
        return faceSystemDao.getById(1);
    }

    @Override
    public boolean checkUser(String userName, String password) {
        Face_System faceSystem=faceSystemDao.getById(1);
        String passwordMd5= MD5Util.stringToMD5(password);
        if(userName.equals(faceSystem.getUserName()) && passwordMd5.equals(faceSystem.getPassword())){
           return true;
        }
        return false;
    }

    @Override
    public long editPassword(String password) {
        return faceSystemDao.editPassword(password);
    }
}
