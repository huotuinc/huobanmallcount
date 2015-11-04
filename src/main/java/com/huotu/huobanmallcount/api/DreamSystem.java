/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.api;

import com.huotu.huobanmallcount.api.common.ApiResult;
import com.huotu.huobanmallcount.api.common.Output;
import com.huotu.huobanmallcount.model.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 梦想计划
 * Created by lgh on 2015/10/17.
 */
public interface DreamSystem {


    /**
     * 统计数据
     *
     * @param data
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult total(Output<DreamTotalModel> data, Integer userId);


    /**
     * 榜单
     *
     * @param data
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult top(Output<DreamTopModel[]> data, Integer merchantId);

    /**
     * 第几名
     *
     * @param top
     * @param merchantId
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult mytop(Output<Integer> top, Integer merchantId, Integer userId);

    /**
     * 5%收益来源
     *
     * @param data
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult fivesource(Output<DreamSourceModel[]> data, Integer userId, Integer pageSize, Integer lastId);

    /**
     * 2%收益来源
     *
     * @param data
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult twosource(Output<DreamSourceModel[]> data, Integer userId, Integer pageSize, Integer lastId);


    /**
     * 返利列表
     *
     * @param data
     * @param pageSize
     * @param page
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult rebatelist(Output<DreamRebateListModel[]> data, Integer merchantId, Integer pageSize, Integer page, String userName, Long startTime, Long endTime);


    /**
     * 返利总数
     *
     * @param data
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult rebatecount(Output<Integer> data, Integer merchantId, String userName, Long startTime, Long endTime);





    /**
     * 分摊列表
     *
     * @param data
     * @param merchantId
     * @param pageSize
     * @param page
     * @param userName
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult sharelist(Output<DreamShareListModel[]> data, Integer merchantId, Integer pageSize, Integer page, String userName);


    /**
     * 分摊总数
     *
     * @param data
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult sharecount(Output<Integer> data, Integer merchantId, String userName);


    @RequestMapping(method = RequestMethod.GET)
    ApiResult rebatedetail(Output<DreamShareDetailModel[]> fiveData,Output<DreamShareDetailModel[]> twoData, Integer userId,Long time);

    @RequestMapping(method = RequestMethod.GET)
    ApiResult sharedetail(Output<DreamShareDetailModel[]> fiveData,Output<DreamShareDetailModel[]> twoData, Integer userId);

    @RequestMapping(method = RequestMethod.GET)
    ApiResult recount();

}
