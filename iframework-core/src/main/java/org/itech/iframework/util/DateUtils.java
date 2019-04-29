package org.itech.iframework.util;

import java.util.Calendar;
import java.util.Date;

/**
 * DateUtils
 *
 * @author liuqiang
 */
public final class DateUtils {
    /**
     * 获取月份第一天
     *
     * @param date 日期
     * @return 月份第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        return getFirstDayOfMonth(date, 0);
    }

    /**
     * 获取月份第一天
     *
     * @param date        日期
     * @param monthAmount 月数
     * @return 月份第一天
     */
    public static Date getFirstDayOfMonth(Date date, int monthAmount) {
        Calendar cale = Calendar.getInstance();

        cale.setTime(date);
        cale.add(Calendar.MONTH, monthAmount);
        cale.set(Calendar.DAY_OF_MONTH, 1);

        return cale.getTime();
    }

    /**
     * 获取月份最后一天
     *
     * @param date 日期
     * @return 月份最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        return getLastDayOfMonth(date, 0);
    }

    /**
     * 获取月份最后一天
     *
     * @param date        日期
     * @param monthAmount 月数
     * @return 月份最后一天
     */
    public static Date getLastDayOfMonth(Date date, int monthAmount) {
        Calendar cale = Calendar.getInstance();

        cale.setTime(date);
        cale.add(Calendar.MONTH, monthAmount);
        cale.set(Calendar.DAY_OF_MONTH, 0);

        return cale.getTime();
    }

    /**
     * 获取周一日期
     *
     * @param date 日期
     * @return 周一日期
     */
    public static Date getFirstDayOfWeek(Date date) {
        return getFirstDayOfWeek(date, 0);
    }

    /**
     * 获取周一日期
     *
     * @param date       日期
     * @param weekAmount 周数
     * @return 周末日期
     */
    public static Date getFirstDayOfWeek(Date date, int weekAmount) {
        Calendar cale = Calendar.getInstance();

        cale.setTime(date);
        cale.add(Calendar.WEEK_OF_YEAR, weekAmount);
        cale.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return cale.getTime();
    }

    /**
     * 获取周日日期
     *
     * @param date 日期
     * @return 周日日期
     */
    public static Date getLastDayOfWeek(Date date) {
        return getLastDayOfWeek(date, 0);
    }

    /**
     * 获取周日日期
     *
     * @param date       日期
     * @param weekAmount 周数
     * @return 周日日期
     */
    public static Date getLastDayOfWeek(Date date, int weekAmount) {
        Calendar cale = Calendar.getInstance();

        cale.setTime(date);
        cale.add(Calendar.WEEK_OF_YEAR, weekAmount);
        cale.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return cale.getTime();
    }

    /**
     * 获取年第一天
     *
     * @param date 日期
     * @return 年第一天
     */
    public static Date getFirstDayOfYear(Date date) {
        return getFirstDayOfYear(date, 0);
    }

    /**
     * 获取年第一天
     *
     * @param date       日期
     * @param yearAmount 年数
     * @return 年第一天
     */
    public static Date getFirstDayOfYear(Date date, int yearAmount) {
        Calendar cale = Calendar.getInstance();

        cale.setTime(date);
        cale.add(Calendar.YEAR, yearAmount);
        cale.set(Calendar.DAY_OF_YEAR, 1);

        return cale.getTime();
    }

    /**
     * 获取年最后一天
     *
     * @param date 日期
     * @return 年最后一天
     */
    public static Date getLastDayOfYear(Date date) {
        return getLastDayOfYear(date, 0);
    }

    /**
     * 获取年最后一天
     *
     * @param date       日期
     * @param yearAmount 年数
     * @return 年最后一天
     */
    public static Date getLastDayOfYear(Date date, int yearAmount) {
        Calendar cale = Calendar.getInstance();

        cale.setTime(date);
        cale.add(Calendar.YEAR, yearAmount);
        cale.set(Calendar.DAY_OF_YEAR, cale.getActualMaximum(Calendar.DAY_OF_YEAR));

        return cale.getTime();
    }
}
