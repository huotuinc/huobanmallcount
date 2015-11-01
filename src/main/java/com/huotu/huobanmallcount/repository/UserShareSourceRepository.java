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
