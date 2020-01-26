package com.skyrity.bean;

import java.util.Date;

/**
 * @author ： VULCAN
 * @date ：2019/12/24 19:46
 * @description : ${description}
 * @path : com.skyrity.db.bean.JsonResult
 * @modifiedBy ：
 */
public class JsonResult {

   public static int RET_SUCCESS=0;
   public static int RET_ERROR=-1;

   private int result_code;
   private String result_msg;
   private long result_time;
   private Object result_data;


    @Override
    public String toString() {
        return "{" +
                "result_code=" + result_code +
                ", result_msg='" + result_msg + '\'' +
                ", result_time=" + result_time +
                ", result_data=" + result_data +
                '}';
    }

    public JsonResult(int result_code, String result_msg, Object result_data) {
        this.result_code = result_code;
        this.result_msg = result_msg;
        this.result_time = new Date().getTime();
        this.result_data = result_data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    public long getResult_time() {
        return result_time;
    }

    public void setResult_time(long result_time) {
        this.result_time = result_time;
    }

    public Object getResult_data() {
        return result_data;
    }

    public void setResult_data(Object result_data) {
        this.result_data = result_data;
    }
}
