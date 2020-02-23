package com.skyrity.dao;

import com.skyrity.bean.Face_Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author ： VULCAN
 * @date ：2020/02/22 14:34
 * @description : ${description}
 * @path : com.skyrity.dao.FaceProjectDao
 * @modifiedBy ：
 */
@Repository
public interface FaceProjectDao {

    @Select("select * from Face_Project where projectNo=#{projectNo}")
    Face_Project getByProjectNo(@Param("projectNo") String projectNo);

    @Select("select * from Face_Project where id=#{id}")
    Face_Project getById(@Param("id") int id);

    @Update("update Face_Project set password=#{password} where id=#{id}")
    long editPassword(@Param("id") int id ,@Param("password") String password);

}

