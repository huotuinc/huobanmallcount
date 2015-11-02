/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.entity;

import com.huotu.huobanmallcount.entity.pk.UserShareRebatePK;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户最终获得的分摊返利
 * Created by lgh on 2015/10/27.
 */

@Entity
@Cacheable(value = false)
@IdClass(value = UserShareRebatePK.class)
public class UserShareRebate {

    private Integer merchantId;

    @Id
    private Integer userId;

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    /**
     * 5%分摊值
     */
    @Column(columnDefinition = "decimal(18,2)")
    private float percentFiveAmount;

    /**
     * 2%分摊值
     */
    @Column(columnDefinition = "decimal(18,2)")
    private float percentTwoAmount;

    /**
     * 5%分摊数量
     */
    private Integer percentFiveNum;

    /**
     * 2%分摊数量
     */
    private Integer percentTwoNum;

    public UserShareRebate(Integer merchantId, Integer userId, Date time, float percentFiveAmount, float percentTwoAmount, Integer percentFiveNum, Integer percentTwoNum) {
        this.merchantId = merchantId;
        this.userId = userId;
        this.time = time;
        this.percentFiveAmount = percentFiveAmount;
        this.percentTwoAmount = percentTwoAmount;
        this.percentFiveNum = percentFiveNum;
        this.percentTwoNum = percentTwoNum;
    }

    public UserShareRebate() {
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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

    public float getPercentFiveAmount() {
        return percentFiveAmount;
    }

    public void setPercentFiveAmount(float percentFiveAmount) {
        this.percentFiveAmount = percentFiveAmount;
    }

    public float getPercentTwoAmount() {
        return percentTwoAmount;
    }

    public void setPercentTwoAmount(float percentTwoAmount) {
        this.percentTwoAmount = percentTwoAmount;
    }

    public Integer getPercentFiveNum() {
        return percentFiveNum;
    }

    public void setPercentFiveNum(Integer percentFiveNum) {
        this.percentFiveNum = percentFiveNum;
    }

    public Integer getPercentTwoNum() {
        return percentTwoNum;
    }

    public void setPercentTwoNum(Integer percentTwoNum) {
        this.percentTwoNum = percentTwoNum;
    }
}
