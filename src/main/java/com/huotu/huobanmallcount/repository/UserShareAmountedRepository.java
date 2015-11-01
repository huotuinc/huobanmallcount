package com.huotu.huobanmallcount.repository;


import com.huotu.huobanmallcount.entity.UserShareAmounted;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2015/10/27.
 */

@Repository
public interface UserShareAmountedRepository  extends JpaRepository<UserShareAmounted, Integer>,ClassicsRepository<UserShareAmounted> {
}
