package com.huotu.huobanmallcount.repository;

import com.huotu.huobanmallcount.entity.UserShareRebateSource;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2015/10/27.
 */

@Repository
public interface UserShareRebateSourceRepository extends JpaRepository<UserShareRebateSource,Long>,ClassicsRepository<UserShareRebateSource> {
}
