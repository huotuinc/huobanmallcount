/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.concurrency;

import com.huotu.huobanmallcount.entity.UserShareAmounted;
import com.huotu.huobanmallcount.model.TreeNode;

import java.util.List;

/**
 * Created by lgh on 2015/10/9.
 */

public interface SystemCounting {

    List<UserShareAmounted> countFromLeaf(TreeNode node);

    void countAll();

    void countMerchant(int merchantId);
}
