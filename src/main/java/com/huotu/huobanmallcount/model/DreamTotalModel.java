package com.huotu.huobanmallcount.model;

/**
 * 梦想计划首页
 * Created by lgh on 2015/10/17.
 */
public class DreamTotalModel {

    /**
     * 5%团队数量
     */
    private Integer fiveNum;

    /**
     * 5%总业绩
     */
    private  Float fiveTotal;

    /**
     * 5%收益
     */
    private Float fiveIncome;

    /**
     * 2%团队数量
     */
    private Integer twoNum;

    /**
     * 2%总业绩
     */
    private Float twoTotal;

    /**
     * 2%收益
     */
    private Float twoIncome;


    public Integer getFiveNum() {
        return fiveNum;
    }

    public void setFiveNum(Integer fiveNum) {
        this.fiveNum = fiveNum;
    }

    public Float getFiveTotal() {
        return fiveTotal;
    }

    public void setFiveTotal(Float fiveTotal) {
        this.fiveTotal = fiveTotal;
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

    public Float getTwoTotal() {
        return twoTotal;
    }

    public void setTwoTotal(Float twoTotal) {
        this.twoTotal = twoTotal;
    }

    public Float getTwoIncome() {
        return twoIncome;
    }

    public void setTwoIncome(Float twoIncome) {
        this.twoIncome = twoIncome;
    }
}
