/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.model;

import java.util.Date;

/**
 * Created by lgh on 2015/10/28.
 */
public class DreamShareListModel {

    private  Integer userId;
    /**
     * 用户名
     */
    private String userName;

    private String  nickName;
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


    public DreamShareListModel() {
    }

    public DreamShareListModel(Integer userId, String userName, String nickName, Integer fiveNum, Float fiveIncome, Integer twoNum, Float twoIncome) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.fiveNum = fiveNum;
        this.fiveIncome = fiveIncome;
        this.twoNum = twoNum;
        this.twoIncome = twoIncome;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
