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
