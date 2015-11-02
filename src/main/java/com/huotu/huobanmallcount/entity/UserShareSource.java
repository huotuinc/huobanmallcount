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


/**
 * 用户分摊的具体来源
 * 前一天的数据
 * Created by lgh on 2015/10/15.
 */
@Entity
@Cacheable(value = false)
public class UserShareSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private Integer userId;


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

    public UserShareSource(Integer userId, Integer type, Integer sourceUserId, Float amount) {
        this.userId = userId;
        this.type = type;
        this.sourceUserId = sourceUserId;
        this.amount = amount;
    }

    public UserShareSource() {
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
