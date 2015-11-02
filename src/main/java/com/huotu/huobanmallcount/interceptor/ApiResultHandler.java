/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.interceptor;

import com.huotu.huobanmallcount.api.common.ApiResult;
import com.huotu.huobanmallcount.interceptor.OutputHandler;
import com.huotu.huobanmallcount.interceptor.PhysicalApiResult;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * Created by lgh on 2015/8/26.
 */
public class ApiResultHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getParameterType() == ApiResult.class;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        ApiResult result = (ApiResult) returnValue;
        PhysicalApiResult rr = new PhysicalApiResult();

        if (result == null) {
            rr.setSystemResultCode(0);
            rr.setSystemResultDescription("返回了无效业务结果");
        } else {
            rr.setSystemResultCode(1);
            rr.setResultCode(result.getResultCode().getValue());
            if (result.getResultDescription() == null) {
                rr.setResultDescription(result.getResultCode().getName());
            }
        }

        Map resultData = (Map) mavContainer.getModel().get(OutputHandler.KeyResultData);
        if (resultData != null && !resultData.isEmpty())
            rr.setResultData(resultData);

        mavContainer.getModel().put("__realResult", rr);

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

        jsonView.setContentType("application/json;charset=UTF-8");
        jsonView.setModelKey("__realResult");
        jsonView.setExtractValueFromSingleKeyModel(true);

        mavContainer.setView(jsonView);
    }
}
