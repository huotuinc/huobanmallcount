package com.huotu.huobanmallcount.model;

/**
 * 排行
 * Created by lgh on 2015/10/17.
 */
public class DreamTopModel {

    private  String imageUrl;

    private String name;

    private Float money;



    public DreamTopModel() {
    }

    public DreamTopModel(String imageUrl, String name, Float money) {
        this.imageUrl = imageUrl;
        this.name = name;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
