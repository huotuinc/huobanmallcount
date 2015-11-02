/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.repository;

import com.huotu.huobanmallcount.entity.UserShare;

import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



/**
 * Created by lgh on 2015/10/14.
 */

@Repository
public interface UserShareRepository extends JpaRepository<UserShare, Integer>, ClassicsRepository<UserShare> {

    @Query("select share from UserShare share where share.userId=?1")
    UserShare findByUserId(Integer userId);

    @Query("select count(userShare) from UserShare  userShare where userShare.merchantId=?1")
    Integer countByMerchantId(Integer merchantId);
}
