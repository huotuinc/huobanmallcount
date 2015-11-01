package com.huotu.huobanmallcount.repository;

import com.huotu.huobanmallcount.entity.User;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lgh on 2015/10/12.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>,ClassicsRepository<User> {

    @Query("select user from User user where user.parentId=0 or user.parentId is null")
    List<User> findTop();

    @Query("select user from User user where user.merchant.id=?1 and (user.parentId=0 or user.parentId is null)")
    List<User> findTop(Integer merchantId);

    @Query("select user from User user where user.parentId=?1")
    List<User> findChildren(Integer parentId);


    @Query("select user from User user where user.merchant.id=?1")
    List<User> findByMerchantId(Integer merchantId);

    @Query("select user.parentId from  User user where user.merchant.id=?1 and user.parentId>=0 group by user.parentId order by user.parentId")
    List<Integer> findParents(Integer merchantId);


    User findByUsername(String username);
}
