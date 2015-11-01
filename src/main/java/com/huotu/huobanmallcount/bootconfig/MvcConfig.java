package com.huotu.huobanmallcount.bootconfig;

import com.huotu.huobanmallcount.interceptor.ApiResultHandler;
import com.huotu.huobanmallcount.interceptor.OutputHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by lgh on 2015/8/19.
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.huotu.huobanmallcount.controller")

public class MvcConfig extends WebMvcConfigurerAdapter {


    /**
     * 设置servlethandler
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 设置控制器方法参数化输出
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new OutputHandler());
    }

    /**
     * 监听 控制器的ApiResult返回值
     *
     * @param returnValueHandlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new ApiResultHandler());
    }

}
