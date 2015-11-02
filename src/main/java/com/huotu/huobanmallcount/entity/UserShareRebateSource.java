/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 返利来源
 * Created by lgh on 2015/10/27.
 */
@Entity
@Cacheable(value = false)
public class UserShareRebateSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private Integer userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;


    /**
     * 类型 1 5%的分摊数据 2 2%的分摊数据
     */
    private Integer type;

    /**
     * 来源用户Id
     */
    private Integer sourceUserId;

    /**
     * 贡献的金额
     */
    @Column(columnDefinition = "decimal(18,2)")
    private Float amount;

    public UserShareRebateSource(Integer userId, Date time, Integer type, Integer sourceUserId, Float amount) {
        this.userId = userId;
        this.time = time;
        this.type = type;
        this.sourceUserId = sourceUserId;
        this.amount = amount;
    }

    public UserShareRebateSource() {
    }




    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Integer sourceUserId) {
        this.sourceUserId = sourceUserId;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
