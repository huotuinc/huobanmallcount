/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.api.common;


import com.huotu.huobanmallcount.config.CommonEnum;

/**
 * App期望获得的api响应
 * @author CJ
 */
public class ApiResult {

    /**
     * 逻辑状态返回 ：1成功,0 失败
     */
    private CommonEnum.AppCode resultCode;
    /**
     * 逻辑状态描述
     */
    private String resultDescription;

    public static ApiResult resultWith(CommonEnum.AppCode code){
        return resultWith(code,null);
    }

    public static ApiResult resultWith(CommonEnum.AppCode code,String description){
        ApiResult result = new ApiResult();
        result.setResultCode(code);
        result.setResultDescription(description);
        return result;
    }

    public CommonEnum.AppCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(CommonEnum.AppCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }
}
