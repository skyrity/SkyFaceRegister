package com.skyrity.service;

import com.skyrity.bean.Face_Project;
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
    /**
     * PC浏览器端用户登录接口
     *
     * @param request  请求对象
     * @param userName 用户名
     * @param password 密码
     * @return 返回登录成功与否JSON信息
     * Data:{
     * “result_code”:”0”,               //返回码
     * ”result_msg”:”返回信息”,          //信息描述
     * “result_time”:”执行时间”,         //登陆时间
     * “result_data”: {
     * “accessToken”:”访问令牌”
     * }
     * }
     * @throws Base64DecodingException 异常
     */
    String login(HttpServletRequest request, String userName, String password) throws Base64DecodingException;

    /**
     * PC浏览器端用户注销
     *
     * @param request 请求对象
     * @return 返回登录成功与否JSON信息
     * Data:{
     * “result_code”:”0”,					//返回码
     * ”result_msg”:”返回信息”,			//信息描述
     * “result_time”:”执行时间”,			//登陆时间
     * “result_data”: {}
     * }
     */
    String logout(HttpServletRequest request);

    /**
     * 获得人脸数据信息
     *
     * @param request  请求对象
     * @param state    状态位
     * @param fields   查询的字段
     * @param pageNum  页号
     * @param pageSize 页大小
     * @return 返回JSON数据信息
     * Data:{
     * “result_code”:”0”,			  //返回码
     * ”result_msg”:”返回信息”,		  //信息描述
     * “result_time”:”执行时间”		  //获取时间
     * “result_data”:{                //返回数据
     * “currentPage”:1,               //当前页号
     * “pageSize”:30,                 //页记录大小
     * “totalPage”:1,                 //总页数
     * “totalRecord”:1,               //总记录数
     * “dataList”:[                   //记录数组
     * { “id”:”记录ID”,
     * “name”:”访客名”,
     * “telNo”:”访客手机号”,
     * “applyTime”:”申请时间”,
     * “imgUrl”:”图片下载地址”,
     * “cardNo”:”卡号”,
     * “state”:”处理状态”
     * },
     * ...]
     * }
     * }
     */
    String getfaces(HttpServletRequest request, int state, String fields, int pageNum, int pageSize);

    /**
     * 注册信息审核提交接口
     *
     * @param request 请求对象
     * @param ids     录ID，多个ID，中间用”,”分隔” 如："12,13"
     * @param isPass  是否通过0=拒绝，1=通过，2=已处理
     * @return Data:{
     * “result_code”:”0”,				//返回码
     * ”result_msg”:”返回信息”,			//信息描述
     * “result_time”:”执行时间”			//执行时间
     * “result_data”:””
     * }
     */
    String approve(HttpServletRequest request, String ids, int isPass);

    /**
     * 人脸注册
     *
     * @param request 请求对象
     * @param file    图片
     * @return Data:{
     * “result_code”:”0”,			     //返回码
     * ”result_msg”:”返回信息”,			//信息描述
     * “result_time”:”执行时间”			//申请时间
     * “result_data”: {
     * “imgUrl”:”图片下载地址”
     * }
     * }
     */
    String register(HttpServletRequest request, MultipartFile file);

    /**
     * @param request  请求对象
     * @param password 密码
     * @return Data:{
     * “result_code”:”0”,					//返回码
     * ”result_msg”:”返回信息”,			//信息描述
     * “result_time”:”执行时间”,			//登陆时间
     * “result_data”: {}
     * }
     */
    long editPassword(HttpServletRequest request, String password);

    /**
     * 用户检查
     *
     * @param userName 用户名
     * @param password 密码
     * @return 是否有效用户
     */
    boolean checkUser(String userName, String password);

    /**
     * 获得系统配置
     *
     * @return
     */
    Face_System getSystem();

    /**
     * @param request  请求对象
     * @param projectId 项目
     * @param password 密码
     * @return Data:{
     * “result_code”:”0”,				//返回码
     * ”result_msg”:”返回信息”,			//信息描述
     * “result_time”:”执行时间”,			//登陆时间
     * “result_data”: {}
     * }
     */
    long editProjectPass(HttpServletRequest request, int projectId ,String password);

    /**
     * PC应用程序端用户登录接口
     *
     * @param request  请求对象
     * @param userName 用户名
     * @param password 密码
     * @return 返回登录成功与否JSON信息
     * Data:{
     * “result_code”:”0”,               //返回码
     * ”result_msg”:”返回信息”,          //信息描述
     * “result_time”:”执行时间”,         //登陆时间
     * “result_data”: {
     * “accessToken”:”访问令牌”
     * }
     * }
     * @throws Base64DecodingException 异常
     */
    String pcLogin(HttpServletRequest request, String userName, String password) throws Base64DecodingException;

    /**
     * 项目用户检查
     *
     * @param projectNo 项目名
     * @param password 密码
     * @return 是否有效项目
     */
    boolean checkProjectUser(String projectNo, String password);


    Face_Project getProjectById(int projectId);

}
