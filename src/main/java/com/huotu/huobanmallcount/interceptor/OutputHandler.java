/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.interceptor;

import com.huotu.huobanmallcount.api.common.Output;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lgh on 2015/8/26.
 */
public class OutputHandler implements HandlerMethodArgumentResolver {

    public final static String KeyResultData = "_resultData";
    private final static Pattern classNameFetcher = Pattern.compile(".*<(.+)>");

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

//        parameter.getParameterName() 是参数名字
//        parameter.getGenericParameterType() 是携带T的类型  getTypeName() <java.lang.String>
//        parameter.getParameterType() 是一般类型


        //
        return parameter.getParameterType() == Output.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 在modal中 设置一个固定参数用于保存resultData
        Map<String, Object> map = (Map) mavContainer.getModel().get(KeyResultData);
        if (map == null) {
            map = new HashMap();
            mavContainer.getModel().put(KeyResultData, map);
        }

        String nameConter = parameter.getGenericParameterType().getTypeName();

        Matcher mc = classNameFetcher.matcher(nameConter);

        Class targetClass = Object.class;
        if (mc.matches()) {
            String className = mc.group(1);
            targetClass = ClassUtils.resolveClassName(className, Thread.currentThread().getContextClassLoader());
        }
        return new IOutput(map, parameter.getParameterName(), targetClass);
    }

    private class IOutput<T> implements Output<T> {

        private final Map<String, Object> map;
        private final String name;
        private final Class targetClass;

        public IOutput(Map<String, Object> map, String name, Class targetClass) {
            this.map = map;
            this.name = name;
            this.targetClass = targetClass;
        }

        @Override
        public <T1 extends T> void outputData(T1 data) {
            if (data != null) {
                if (!targetClass.isInstance(data)) {
                    throw new IllegalArgumentException("参数" + name + "期待类型" + targetClass + ",而不是" + data);
                }
                map.put(name, data);
            } else
                map.put(name, data);
        }
    }

}
