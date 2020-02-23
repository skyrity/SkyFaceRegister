package com.skyrity.service;

import com.skyrity.bean.Face_Project;

/**
 * @author ： VULCAN
 * @date ：2020/02/22 15:01
 * @description : ${description}
 * @path : com.skyrity.service.FaceProjectService
 * @modifiedBy ：
 */
public interface FaceProjectService {

    /**
     * 获得项目对象
     * @param id 项目id
     * @return 项目对象
     */
    Face_Project getById(int id);

    /**
     * 获得项目对象
     * @param proejctNo 项目号
     * @return 项目对象
     */
    Face_Project getByProjectNo(String proejctNo);

    /**
     * 修改项目密码
     * @param projectId 项目号
     * @param password 密码
     * @return 是否正确
     */
    long editProjectPass(int projectId,String password);
}
