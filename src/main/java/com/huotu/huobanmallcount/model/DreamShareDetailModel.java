package com.huotu.huobanmallcount.model;

/**
 * Created by lgh on 2015/10/29.
 */
public class DreamShareDetailModel {

    private String name;
    private String  nickName;
    private Float money;

    public DreamShareDetailModel() {
    }

    public DreamShareDetailModel(String name, String nickName, Float money) {
        this.name = name;
        this.nickName = nickName;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
