package com.skyrity.service;

import com.skyrity.bean.Face_Register;
import com.skyrity.bean.Pager;
import org.apache.ibatis.annotations.Param;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 19:47
 * @description : ${description}
 * @path : com.skyrity.service.FaceRegisterService
 * @modifiedBy ：
 */
public interface FaceRegisterService {

    long add(Face_Register faceRegister);
    long edit(Face_Register faceRegister);

    Pager<Face_Register> getAll(int pageIndex,int pageSize);

    Pager<Face_Register> getFields(int state,String fields,int pageIndex,int pageSize);

    Face_Register getByTelephone(String teFieldslNo);

    long approve(int id,int state);

    Pager<Face_Register> getPager(String in_Where, int pageIndex, int pageSize);
}
