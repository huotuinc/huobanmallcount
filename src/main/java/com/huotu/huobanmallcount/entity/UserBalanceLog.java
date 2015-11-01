package com.huotu.huobanmallcount.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 余额日志
 * Created by lgh on 2015/10/28.
 */

@Entity
@Cacheable(value = false)
@Table(name = "Mall_Advance_Logs")
public class UserBalanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Log_Id")
    private Integer id;

    @Column(name = "Member_Id")
    private Integer userId;


    @Column(name = "Money")
    private float remain;

    @Column(name = "Mtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    /**
     * 付款类型
     */
    @Column(name = "Paymethod")
    private String payType;

    @Column(name = "Memo")
    private String memo;

    /**
     * 金额
     */
    @Column(name = "Import_Money")
    private float money;

    @Column(name = "Explode_Money")
    private float explodeMoney;

    @Column(name = "Member_Advance")
    private float memberAdvance;

    @Column(name = "Shop_Advance")
    private float shopAdvance;

    @Column(name = "Disabled")
    private Integer disabled;

    @Column(name = "Customer_Id")
    private Integer merchantId;

    public UserBalanceLog(Integer userId, float remain, Date time, String payType, String memo, float money, float explodeMoney, float memberAdvance, float shopAdvance, Integer disabled, Integer merchantId) {
        this.userId = userId;
        this.remain = remain;
        this.time = time;
        this.payType = payType;
        this.memo = memo;
        this.money = money;
        this.explodeMoney = explodeMoney;
        this.memberAdvance = memberAdvance;
        this.shopAdvance = shopAdvance;
        this.disabled = disabled;
        this.merchantId = merchantId;
    }

    public UserBalanceLog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public float getRemain() {
        return remain;
    }

    public void setRemain(float remain) {
        this.remain = remain;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getExplodeMoney() {
        return explodeMoney;
    }

    public void setExplodeMoney(float explodeMoney) {
        this.explodeMoney = explodeMoney;
    }

    public float getMemberAdvance() {
        return memberAdvance;
    }

    public void setMemberAdvance(float memberAdvance) {
        this.memberAdvance = memberAdvance;
    }

    public float getShopAdvance() {
        return shopAdvance;
    }

    public void setShopAdvance(float shopAdvance) {
        this.shopAdvance = shopAdvance;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
}
