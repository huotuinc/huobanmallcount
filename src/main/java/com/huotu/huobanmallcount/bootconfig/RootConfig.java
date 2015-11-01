package com.huotu.huobanmallcount.bootconfig;

import org.luffy.lib.libspring.data.ClassicsRepositoryFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Administrator on 2015/8/19.
 */
@Configuration
@ComponentScan(value = {"com.huotu.huobanmallcount.service.impl" ,"com.huotu.huobanmallcount.concurrency"})
@ImportResource(value = {"classpath:spring-jpa.xml"})
@EnableJpaRepositories(value = {"com.huotu.huobanmallcount.repository"}, repositoryFactoryBeanClass = ClassicsRepositoryFactoryBean.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class RootConfig {


}
