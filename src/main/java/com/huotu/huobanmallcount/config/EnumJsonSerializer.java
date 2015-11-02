/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ
 */
public class EnumJsonSerializer implements Converter<ICommonEnum,Map> {

    @Override
    public Map convert(ICommonEnum value) {
        HashMap<String,Object> map = new HashMap();
        map.put("name",value.getName());
        map.put("value",value.getValue());
        return map;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.uncheckedSimpleType(ICommonEnum.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructMapLikeType(Map.class,String.class,Object.class);
    }
}