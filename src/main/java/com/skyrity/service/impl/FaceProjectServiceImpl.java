package com.skyrity.service.impl;

import com.skyrity.bean.Face_Project;
import com.skyrity.dao.FaceProjectDao;
import com.skyrity.service.FaceProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ： VULCAN
 * @date ：2020/02/22 15:01
 * @description : ${description}
 * @path : com.skyrity.service.impl.FaceProjectServiceImpl
 * @modifiedBy ：
 */
@Service("FaceProjectService")
public class FaceProjectServiceImpl implements FaceProjectService {

    @Autowired
    private FaceProjectDao faceProjectDao;

    @Override
    public Face_Project getById(int id) {
        return faceProjectDao.getById(id);
    }

    @Override
    public Face_Project getByProjectNo(String projectNo) {
        return faceProjectDao.getByProjectNo(projectNo);
    }

    @Override
    public long editProjectPass(int projectId,String password) {
        return faceProjectDao.editPassword(projectId,password);
    }
}
