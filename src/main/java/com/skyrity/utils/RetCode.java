package com.skyrity.utils;

public class RetCode {
	public static final String RET_SUCCESS = "{\"result_code\":0,\"result_msg\":\"操作成功\",\"result_time\":RESULT_TIME," +
			"\"result_data\":RESULT_DATA}";
	public static final String RET_ERROR_LOGIN = "{\"result_code\":-1,\"result_msg\":\"用户未登录\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_REGISTER = "{\"result_code\":-2,\"result_msg\":\"获取数据失败\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_PARAMS = "{\"result_code\":-3,\"result_msg\":\"输入参数错误\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_USERPASS = "{\"result_code\":-4,\"result_msg\":\"用户登录失败\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_EXCEPTION = "{\"result_code\":-5,\"result_msg\":\"数据异常错误\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_SESSIONKEY = "{\"result_code\":-6,\"result_msg\":\"获得密码键值错误\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_DECRYPTED = "{\"result_code\":-7,\"result_msg\":\"数据解密错误\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_REGISTERED = "{\"result_code\":-8,\"result_msg\":\"用户已注册，请勿重复注册\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_APPROVE = "{\"result_code\":-9,\"result_msg\":用户审核失败！\"\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_EDITPASSWORD = "{\"result_code\":-10,\"result_msg\":修改密码失败！\"\",\"result_time\":RESULT_TIME}";
	public static final String RET_ERROR_OLDPASSWORD = "{\"result_code\":-11,\"result_msg\":旧密码不符，修改密码失败！\"\",\"result_time\":RESULT_TIME}";

}
