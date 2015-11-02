/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.config;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huotu.huobanmallcount.config.EnumJsonSerializer;


/**
 * 常规枚举类
 * 对app来说 这些信息都会以name,value为key的json展示
 */
@JsonSerialize(converter = EnumJsonSerializer.class)
public interface ICommonEnum {
	int getValue();

	String getName();

}


