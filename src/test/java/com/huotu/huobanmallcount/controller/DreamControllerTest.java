package com.huotu.huobanmallcount.controller;

import com.alibaba.fastjson.JSON;
import com.huotu.huobanmallcount.base.SpringAppTest;
import com.huotu.huobanmallcount.bootconfig.MvcConfig;
import com.huotu.huobanmallcount.bootconfig.RootConfig;
import com.huotu.huobanmallcount.service.DreamService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by lgh on 2015/10/19.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class DreamControllerTest extends SpringAppTest {

    private static final Log log = LogFactory.getLog(DreamControllerTest.class);


    @Autowired
    private DreamService dreamService;

    @Test
    public void testTotal() throws Exception {
//        log.info(JSON.toJSONString(dreamService.total(10057)));

        mockMvc.perform(get("/dream/total").param("userId", "10057"))
                .andDo(print());
    }

    @Test
    public void testTop() throws Exception {
        log.info(JSON.toJSONString(dreamService.top(3677)));
        log.info(JSON.toJSONString(dreamService.myTop(3677, 378383)));
    }


    @Test
    public void testSource() throws Exception {
//        log.info(JSON.toJSONString(dreamService.fivSource(10057)));
//        log.info(JSON.toJSONString(dreamService.twoSource(10057)));
    }
}