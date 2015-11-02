/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lgh on 2015/9/1.
 */
public class DateHelper {

    /**
     * 获取本周第一天 (整点 凌晨)
     *
     * @return
     */
    public static Date getThisWeekBegin() {
        int mondayPlus;
        Calendar calendar = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            mondayPlus = 0;
        } else {
            mondayPlus = 1 - dayOfWeek;
        }

        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        return currentDate.getTime();
    }

    /**
     * 获取本月第一天 (整点 凌晨)
     *
     * @return
     */
    public static Date getThisMonthBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取上个月第一天(整点 凌晨)
     * @return
     */
    public static  Date getLastMonthBegin()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天整点
     *
     * @return
     */
    public static Date getThisDayBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间整点
     *
     * @return
     */
    public static Date getThisDayBegin(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);//从0开始 所以比时间少1
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取昨日凌晨时间
     * @return
     */
    public static Date getYesterdayBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取当前整点
     *
     * @return
     */
    public static Date getThisHourBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date StringToDate(String s, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(s);
    }

    public static Date StringToDate(String s) throws ParseException {
        return StringToDate(s, "yyyy-MM-dd hh:mm:ss");
    }


    /**
     * 获取月的每个周末
     *
     * @param year  年
     * @param month 月
     * @return
     * @throws ParseException
     */
    public static List<Date> getMonthWeekEnd(Integer year, Integer month) throws ParseException {
        List<Date> list = new ArrayList<>();
        String curMonth = year.toString() + "-" + month.toString();

        //获取总天数
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateFormat.parse(curMonth));
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= days; i++) {
            //获取每日数据
            calendar = new GregorianCalendar();
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(dateFormat.parse(curMonth + "-" + i));

            int k = new Integer(calendar.get(Calendar.DAY_OF_WEEK));
            if (k == 1) {
                // 若当天是周日
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                list.add(df.parse(curMonth + "-" + i));
            }
            if (k != 1 && i == days) {
                // 若是本月最后一天，且不是周日
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                list.add(df.parse(curMonth + "-" + i));
            }
        }
        return list;
    }
}
