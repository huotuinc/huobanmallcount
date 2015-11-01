package com.huotu.huobanmallcount.boot;


import com.huotu.huobanmallcount.bootconfig.MvcConfig;
import com.huotu.huobanmallcount.bootconfig.RootConfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by Administrator on 2015/8/19.
 */


public class HuobanmallLoader extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{MvcConfig.class, RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};

    }
}
