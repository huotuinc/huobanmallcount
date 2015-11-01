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
