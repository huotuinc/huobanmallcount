package com.huotu.huobanmallcount.repository;

import com.huotu.huobanmallcount.entity.Merchant;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2015/10/29.
 */


@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer>, ClassicsRepository<Merchant> {
}
