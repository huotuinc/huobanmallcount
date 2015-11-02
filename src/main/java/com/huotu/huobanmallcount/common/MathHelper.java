/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.common;

/**
 * 计算处理
 * Created by Administrator on 2015/8/4.
 */
public class MathHelper {

    /**
     * 处理float精度丢失问题，保留小数点2位
     * @param f
     * @return
     */
    public static float doFloat(float f) {
        return Float.valueOf(Math.round(f * 100)) / 100;
    }
}
