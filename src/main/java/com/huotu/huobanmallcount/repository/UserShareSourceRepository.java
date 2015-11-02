/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.repository;


import com.huotu.huobanmallcount.entity.UserShareSource;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Created by lgh on 2015/10/16.
 */
@Repository
public interface UserShareSourceRepository extends JpaRepository<UserShareSource, Long>,ClassicsRepository<UserShareSource> {

//    @Query("select source from UserShareSource  source where source.userId=?1 and source.type=?2")
//    List<UserShareSource> findByUserIdAndType(Integer userId, Integer type);
}
