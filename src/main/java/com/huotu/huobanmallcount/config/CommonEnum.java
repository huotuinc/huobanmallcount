/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.config;


/**
 * 枚举类
 *
 * @author Administrator
 */
public interface CommonEnum {

    enum AppCode implements ICommonEnum {

        /**
         * SUCCESS(1, "操作成功"),
         */
        SUCCESS(1, "操作成功"),

        /**
         * SYSTEM_BAD_REQUEST(50001, "系统请求失败"),
         */
        SYSTEM_BAD_REQUEST(50001, "系统请求失败");


        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        AppCode(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }


}
