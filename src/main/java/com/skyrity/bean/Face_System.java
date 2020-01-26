package com.skyrity.bean;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 19:36
 * @description : ${description}
 * @path : com.skyrity.bean.Face_System
 * @modifiedBy ：
 */
public class Face_System {

    public final static int STATE_NOAPPROVE=0;
    public final static int STATE_APPROVE=1;

    long id; // 主键ID
    long state; //是否审核：0-否；1-是
    String userName;//用户名称
    String password;//密码

    @Override
    public String toString() {
        return "Face_System{" +
                "id=" + id +
                ", state=" + state +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
