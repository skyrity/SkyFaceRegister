package com.skyrity.dao;

import com.skyrity.bean.Face_Register;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 19:39
 * @description : ${description}
 * @path : FaceRegisterDao
 * @modifiedBy ：
 */
@Repository
public interface FaceRegisterDao {

    @Select("{call dbo.Page(" +
            "#{map.out_PageCount,mode=OUT,jdbcType=INTEGER},"+ //总页数输出
            "#{map.out_ResultCount,mode=OUT,jdbcType=INTEGER},"+ //总记录数输出
            "#{map.out_SQL,mode=OUT,jdbcType=NVARCHAR},"  + //返回数据库SQL查询语句
            "#{map.in_Table,mode=IN,jdbcType=NVARCHAR},"+ //查询表名
            "#{map.in_Key,mode=IN,jdbcType=VARCHAR}," + //主键
            "#{map.in_Fields,mode=IN,jdbcType=NVARCHAR}," + //查询字段
            "#{map.in_Where,mode=IN,jdbcType=NVARCHAR}," + //查询条件
            "#{map.in_Order,mode=IN,jdbcType=NVARCHAR}," + //排序字段
            "#{map.in_Begin,mode=IN,jdbcType=INTEGER}," + //开始位置
            "#{map.in_PageIndex,mode=IN,jdbcType=INTEGER}," + //当前页数
            "#{map.in_PageSize,mode=IN,jdbcType=INTEGER})}") //页大小
    @Options(statementType= StatementType.CALLABLE)
    List<Face_Register> getByAttrs(@Param("map") Map<String,String> map);

    @Select("select Top 1 * from Face_Register where telNo=#{telNo}")
    Face_Register getByTelephone(String telNo);

    /**
     * 增加注册人脸记录
     * @param faceRegister 人脸记录
     * @return 是否成功 >0 成功  =0失败
     */
    @Insert("insert into Face_Register(name,openId,telNo,applyTime,state,imgUrl,cardNo) " +
            "values(#{faceRegister.name},#{faceRegister.openId},#{faceRegister.telNo},getDate(),#{faceRegister.state}," +
            "#{faceRegister.imgUrl},#{faceRegister.cardNo})")
    long add(@Param("faceRegister") Face_Register faceRegister);

    @Update("Update Face_Register Set name=#{faceRegister.name},openId=#{faceRegister.openId},applyTime=getDate(),state=#{faceRegister.state}, " +
            "imgUrl=#{faceRegister.imgUrl},cardNo=#{faceRegister.cardNo} " +
            " where id=#{faceRegister.id}")
    long edit(@Param("faceRegister") Face_Register faceRegister);
    /**
     * 修改注册状态
     * @param id 记录
     * @param state 人脸状态
     * @return 是否成功 >0 成功  =0失败
     */
    @Update("update Face_Register set state=#{state} where id=#{id} and state<>2")
    long approve(@Param("id") int id,@Param("state") int state);

}
