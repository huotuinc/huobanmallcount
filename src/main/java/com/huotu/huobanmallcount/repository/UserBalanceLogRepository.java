package com.huotu.huobanmallcount.repository;

import com.huotu.huobanmallcount.entity.UserBalanceLog;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2015/10/28.
 */

@Repository
public interface UserBalanceLogRepository extends JpaRepository<UserBalanceLog,Integer>,ClassicsRepository<UserBalanceLog> {
}
