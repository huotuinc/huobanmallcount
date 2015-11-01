package com.huotu.huobanmallcount.model;

import java.util.Date;

/**
 * Created by lgh on 2015/10/26.
 */
public class DreamRebateListModel {

    private  Integer userId;
    /**
     * 用户名
     */
    private String userName;

    private String  nickName;
    /**
     * 时间
     */
    private Date time;

    /**
     * 5%团队数量
     */
    private Integer fiveNum;

    /**
     * 5%收益
     */
    private Float fiveIncome;

    /**
     * 2%团队数量
     */
    private Integer twoNum;

    /**
     * 2%收益
     */
    private Float twoIncome;


    public DreamRebateListModel() {
    }

    public DreamRebateListModel(Integer userId, String userName, String nickName, Date time, Integer fiveNum, Float fiveIncome, Integer twoNum, Float twoIncome) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.time = time;
        this.fiveNum = fiveNum;
        this.fiveIncome = fiveIncome;
        this.twoNum = twoNum;
        this.twoIncome = twoIncome;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public Integer getFiveNum() {
        return fiveNum;
    }

    public void setFiveNum(Integer fiveNum) {
        this.fiveNum = fiveNum;
    }

    public Float getFiveIncome() {
        return fiveIncome;
    }

    public void setFiveIncome(Float fiveIncome) {
        this.fiveIncome = fiveIncome;
    }

    public Integer getTwoNum() {
        return twoNum;
    }

    public void setTwoNum(Integer twoNum) {
        this.twoNum = twoNum;
    }

    public Float getTwoIncome() {
        return twoIncome;
    }

    public void setTwoIncome(Float twoIncome) {
        this.twoIncome = twoIncome;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
