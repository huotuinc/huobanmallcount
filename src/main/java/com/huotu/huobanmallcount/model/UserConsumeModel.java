package com.huotu.huobanmallcount.model;

/**
 * Created by lgh on 2015/10/12.
 */
public class UserConsumeModel {
    private Integer userId;
    private Float money;

    public UserConsumeModel(Integer userId, Float money) {
        this.userId = userId;
        this.money = money;
    }

    public UserConsumeModel() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
