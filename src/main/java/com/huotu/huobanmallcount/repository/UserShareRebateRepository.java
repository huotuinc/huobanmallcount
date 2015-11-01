package com.huotu.huobanmallcount.repository;

import com.huotu.huobanmallcount.entity.UserShareRebate;
import com.huotu.huobanmallcount.entity.pk.UserShareRebatePK;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2015/10/27.
 */

@Repository
public interface UserShareRebateRepository extends JpaRepository<UserShareRebate, UserShareRebatePK>, ClassicsRepository<UserShareRebate> {

    @Query("select count(userShare) from UserShareRebate  userShare where userShare.merchantId=?1")
    Integer countByMerchantId(Integer merchantId);
}
