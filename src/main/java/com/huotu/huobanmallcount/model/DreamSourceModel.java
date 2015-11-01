package com.huotu.huobanmallcount.model;

/**
 * 来源数据
 * Created by lgh on 2015/10/17.
 */
public class DreamSourceModel {

    private Long id;

    private String imageUrl;

    private String name;

    private Float money;


    public DreamSourceModel() {
    }

    public DreamSourceModel(Long id, String imageUrl, String name, Float money) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
