package com.skyrity.dao;

import com.skyrity.bean.Face_System;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 19:41
 * @description : ${description}
 * @path : FaceSystemDao
 * @modifiedBy ：
 */
@Repository
public interface FaceSystemDao {
    @Select("select * from Face_System where id=#{id}")
    Face_System getById(int id);

    @Update("update Face_System set password=#{password} where id=1")
    long editPassword(@Param("password") String password);
}
