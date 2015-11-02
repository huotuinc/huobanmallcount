/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.service;


import com.huotu.huobanmallcount.model.*;

import java.util.List;

/**
 * Created by lgh on 2015/10/19.
 */
public interface DreamService {

    /**
     * 统计数据(天)
     *
     * @param userId
     * @return
     */
    DreamTotalModel total(Integer userId);


    /**
     * 榜单总排行
     *
     * @return
     */
    List<DreamTopModel> top(Integer merchantId);


    /**
     * 我在榜单中的位置
     *
     * @param merchantId
     * @param userId
     * @return
     */
    Integer myTop(Integer merchantId, Integer userId);


    /**
     * 5%收益来源 (天)
     *
     * @param userId
     * @return
     */
    List<DreamSourceModel> fivSource(Integer userId, Integer pageSize, Integer lastId);

    /**
     * 2%收益来源（天）
     *
     * @param userId
     * @return
     */
    List<DreamSourceModel> twoSource(Integer userId, Integer pageSize, Integer lastId);

    /**
     * 返利列表
     *
     * @param merchantId
     * @param pageSize
     * @param page
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    List<DreamRebateListModel> rebateList(Integer merchantId, Integer pageSize, Integer page, String userName, Long startTime, Long endTime);

    /**
     * 返利总数
     *
     * @param merchantId
     * @return
     */
    Integer rebateCount(Integer merchantId, String userName, Long startTime, Long endTime);

    List<DreamShareListModel> shareList(Integer merchantId, Integer pageSize, Integer page, String userName);

    Integer shareCount(Integer merchantId, String userName);


    List<DreamShareDetailModel> rebateFiveDetail(Integer userId, Long time);

    List<DreamShareDetailModel> rebateTwoDetail(Integer userId, Long time);


    List<DreamShareDetailModel> shareFiveDetail(Integer userId);

    List<DreamShareDetailModel> shareTwoDetail(Integer userId);


}
