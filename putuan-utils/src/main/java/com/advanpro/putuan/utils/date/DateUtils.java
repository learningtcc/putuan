package com.advanpro.putuan.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {说明}
 *
 * @author Retina.Ye
 * @since 2014/11/4 14:12
 */
public class DateUtils {

    /**
     * Add by Retina.Ye
     * <p/>
     * 由于数据库的日期字段不能为null，
     * 所以对象的每个日期字段都不能为null,
     * 但是对象保存的时候某些日期字段业务逻辑上设为当前时间是不合理的
     * 所以提供一个规定好的不合法的时间，只要是这个时间，证明该时间其实为null
     */
    public static Date getInvalidDate() {
        return parseDate("1970-01-01 00:00:00");
    }


    /**
     * 获取当前系统时间(原始格式)
     */
    public static Date getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    /**
     * 获取当前时间日期的字符串
     */
    public static String getCurrentDateStr(DateFormatType dateFormatType) {
        Date date = getCurrentDate();
        return (String) doOperation(date, dateFormatType.getValue());
    }

    /**
     * 获取当前年的第一天日期
     */
    public static Date getCurrentYearFirstDay() {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        return c.getTime();
    }

    /**
     * 时间、日期格式化成字符串
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return "";
        }
        return (String) formatDate(date, DateFormatType.DATE_FORMAT_STR);
    }

    /**
     * 时间、日期格式化成字符串
     */
    public static String formatDate(Date date, DateFormatType dateFormatType) {
        if (null == date) {
            return "";
        }
        return (String) doOperation(date, dateFormatType.getValue());
    }

    /**
     * 时间、日期格式化成字符串
     */
    public static String formatDate(Date date, String dateFormatType) {
        if (null == date) {
            return "";
        }
        return (String) doOperation(date, dateFormatType);
    }

    public static Date parseDate(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Date parseDate(Date date, String format) {
        return parseDate(format(date, "yyyy-MM-dd HH:mm:ss"), format);
    }

    /**
     * 取到到明天还有多少Millis
     *
     * @return
     */
    public static int getToTomorrowTimeSecond(Date date) {
        Date tomorrowDate = DateUtils.parseDate(DateUtils.addDays(date, 1), "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(tomorrowDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date);
        long time2 = cal.getTimeInMillis();
        long betweenTime = (time1 - time2) / 1000;
        return Integer.parseInt(String.valueOf(betweenTime));
    }


    /**
     * 为日期增加秒数
     *
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date date, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    public static Date addHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    public static Date addWeek(Date date, int weeks) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, weeks);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, DateFormatType.DATE_FORMAT_STR);
    }

    /**
     * 从字符串解析成时间、日期
     */
    public static Date parseDate(String dateStr, DateFormatType dateFormatType) {
        return (Date) doOperation(dateStr, dateFormatType.getValue());
    }

    private static Object doOperation(Object object, String formatStr) {
        if (object == null || null == formatStr || "".equals(formatStr)) {
            throw new RuntimeException("参数不能为空");
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            if (object instanceof Date)
                return format.format(object);
            else
                return format.parse(object.toString());
        } catch (Exception e) {
            throw new RuntimeException("操作失败", e);
        }

    }

    public static String format(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static String format(long dateTime, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date(dateTime));
    }

    /**
     * 取得指定日期N年后的日期
     *
     * @param date
     * @param years 年数（负数则往前）
     * @return
     */
    public static Date addYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    public enum DateFormatType {

        /**
         * yyyy-MM-dd HH:mm:ss
         */
        DATE_FORMAT_STR("yyyy-MM-dd HH:mm:ss"),

        /**
         * yyyyMMddhhmmss
         */
        DATE_FORMAT_FOREX("yyyyMMddhhmmss"),

        /**
         * yyyy-MM-dd HH:mm
         */
        DATE_MINUTE_FORMAT_STR("yyyy-MM-dd HH:mm"),

        /**
         * MM/dd/yyyy HH:mm:ss
         */
        DATE_USA_STYLE("MM/dd/yyyy HH:mm:ss"),

        DATE_USA_STYLE2("yyyy/MM/dd HH:mm:ss"),

        /**
         * yyyy年MM月dd日 HH时mm分ss秒
         */
        DATE_FORMAT_STR_CHINA("yyyy年MM月dd日 HH时mm分ss秒"),

        /**
         * yyyy年MM月dd日 HH点
         */
        DATE_FORMAT_STR_CHINA_HOUR("yyyy年MM月dd日 HH点"),

        /**
         * yy年MM月dd日 HH点
         */
        DATE_FORMAT_STR_CHINA_HOUR_YY("yy年MM月dd日 HH点"),

        /**
         * yyyyMMddHHmmss
         */
        SIMPLE_DATE_TIME_FORMAT_STR("yyyyMMddHHmmss"),

        /**
         * yyyy-MM-dd
         */
        SIMPLE_DATE_FORMAT_STR("yyyy-MM-dd"),

        /**
         * yyyyMMdd
         */
        SIMPLE_DATE_FORMAT("yyyyMMdd"),

        /**
         * yy年MM月dd日
         */
        SIMPLE_DATE_FORMAT_STR_YY("yy年MM月dd日"),

        /**
         * yyyy年MM月dd日
         */
        SIMPLE_DATE_FORMAT_STR_DAY("yyyy年MM月dd日"),

        /**
         * MM月dd日
         */
        SIMPLE_DATE_FORMAT_STR_MD("MM月dd日"),

        /**
         * yyyy/MM/dd
         */
        SIMPLE_DATE_FORMAT_VIRGULE_STR("yyyy/MM/dd"),

        /**
         * HH:mm:ss MM-dd
         */
        MONTH_DAY_HOUR_MINUTE_SECOND("HH:mm:ss MM-dd"),

        /**
         * HH:mm:ss
         */
        HOUR_MINUTE_SECOND("HH:mm:ss"),

        /**
         * HH:mm
         */
        HOUR_MINUTE("HH:mm");

        private final String value;

        DateFormatType(String formatStr) {
            this.value = formatStr;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(long date1, long date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(date1));
        long time1 = cal.getTimeInMillis();
        cal.setTime(new Date(date2));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static boolean compareDate(Date currDate, Date pareDate) {
        try {
            if (currDate.getTime() > pareDate.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }


    public static Date getLastDayOfMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getFirstDayOfMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static int getCurrentMath() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    public static int getDateYear(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.YEAR);
    }

    public static int getCurrentWeek() {
        Calendar cl = Calendar.getInstance();
        return cl.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getWeek(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getMonth(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.MONTH);
    }

    public static int getDay(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.DATE);
    }

    public static int getHour(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.MINUTE);
    }

    public static int getSecond(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return cl.get(Calendar.SECOND);
    }


    public static Date getDateStart(Date date) {
        String str = format(date, "yyyy-MM-dd") + " 00:00:00";
        return parseDate(str, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getDateEnd(Date date) {
        String str = format(date, "yyyy-MM-dd") + " 23:59:59";
        return parseDate(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取本周的第一天
     *
     * @return
     */
    public static Date getWeekFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday周日
        return calendar.getTime();
    }

    /**
     * 本周的最后一天
     *
     * @return
     */
    public static Date getWeekLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        //calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek() + 6); // Saturday周六
        return calendar.getTime();
    }


    public static void main(String[] args) {
        /*Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        System.out.println(df.format(cal.getTime()));
        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        System.out.println(df.format(cal.getTime()));*/

        /*Calendar c = Calendar.getInstance();
        int weekday = c.get(7)-1;
        c.add(5,-weekday);
        System.out.println("本周开始时间："+c.getTime());
        c.add(5,6);
        System.out.println("本周开始结束："+c.getTime());*/
        //System.out.println(DateUtils.formatDate(DateUtils.getDateStart(DateUtils.getWeekFirstDate())));
        //System.out.println(DateUtils.formatDate(DateUtils.getDateStart(DateUtils.getSundayOfThisWeek())));
        //System.out.println(System.currentTimeMillis());
       /* System.out.println("2===:" + DateUtils.formatDate(getWeekFirstDate()));
        System.out.println("3===:" + DateUtils.formatDate(getMondayOfThisWeek()));
        System.out.println("4===:" + DateUtils.formatDate(getSundayOfThisWeek()));*/


        System.out.println(System.currentTimeMillis());
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static Date getMondayOfThisWeek() {
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        // return df.format(c.getTime());
        return c.getTime();
    }


    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static Date getSundayOfThisWeek() {
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        //return df.format(c.getTime());
        return c.getTime();
    }

    public static List<Date> getWeekEveryDay(Date date) {
        List<Date> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Date everyDay = DateUtils.addDays(date, i);
            System.out.println(everyDay + "_" + i + "===" + DateUtils.formatDate(everyDay));
            list.add(everyDay);
        }
        return list;
    }


}
