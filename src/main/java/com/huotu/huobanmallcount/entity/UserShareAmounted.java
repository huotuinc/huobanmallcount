/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 用户已参与分摊的金额
 * 用于就算每次分摊的量
 * Created by lgh on 2015/10/27.
 */
@Entity
@Cacheable(value = false)
public class UserShareAmounted {

    @Id
    private Integer userId;

    /**
     * 已参与分摊的金额
     */
    @Column(columnDefinition = "decimal(18,2)")
    private Float amount;

    public UserShareAmounted(Integer userId, Float amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public UserShareAmounted() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
