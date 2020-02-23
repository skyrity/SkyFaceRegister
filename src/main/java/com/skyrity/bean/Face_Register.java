package com.skyrity.bean;

import java.util.Date;

/**
 * @author ： VULCAN
 * @date ：2020/01/05 19:28
 * @description : ${description}
 * @path : com.skyrity.bean.Face_Register
 * @modifiedBy ：
 */
public class Face_Register {
    public static final int VALUE_ALL=-1;//全部获取
    public static final int VALUE_UNCONFIRMED=0;//未处理
    public static final int VALUE_PASS = 1; //已审核
    public static final int VALUE_HANDLE=2;//已处理
    public static final int VALUE_NOPASS=3;//审核不通过

    long id; // 主键ID
    String name; //名称
    String openId;//微信号
    String telNo;//电话
    Date applyTime;//申请时间
    int state; //状态
    String imgUrl;//申请状态：0-未处理；1-已审核；2-已处理；3-审核不通过；-1-全部获取
    long cardNo;//卡号
    int projectId;//项目

    @Override
    public String toString() {
        return "Face_Register{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", openId='" + openId + '\'' +
                ", telNo='" + telNo + '\'' +
                ", applyTime=" + applyTime +
                ", state=" + state +
                ", imgUrl='" + imgUrl + '\'' +
                ", cardNo=" + cardNo +
                ", projectId=" + projectId +
                '}';
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getCardNo() {
        return cardNo;
    }

    public void setCardNo(long cardNo) {
        this.cardNo = cardNo;
    }
}
