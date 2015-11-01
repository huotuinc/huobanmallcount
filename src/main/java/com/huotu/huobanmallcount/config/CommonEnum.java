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
