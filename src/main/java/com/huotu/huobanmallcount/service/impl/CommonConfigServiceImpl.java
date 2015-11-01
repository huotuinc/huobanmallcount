package com.huotu.huobanmallcount.service.impl;

import com.huotu.huobanmallcount.service.CommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * Created by lgh on 2015/9/23.
 */

@Service
public class CommonConfigServiceImpl implements CommonConfigService {

    @Autowired
    Environment env;

    @Override
    public String getResoureServerUrl() {
        return env.getProperty("huobanmallcount.resources.url", "http://res.51flashmall.com/");
    }


//    @Override
//    public String getWebUrl() {
//        return env.getProperty("huobanmallcount.web.url", "http://apitest.51flashmall.com:8080/huobanmallcount/");
//    }
}
