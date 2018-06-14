package com.ketqi.common.utils;

import com.ketqi.common.PlatformConstant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期时间与字符串互相转换
 * Create at 2011-11-24
 *
 * @author ketqi
 */
public abstract class DateUtils {
    /** 日期格式器--年 */
    public static final String DATE_FORMAT_YEAR = "yyyy";
    /** 日期格式器 */
    public static final String DATE_FORMAT_MONTH = "yyyy-MM";
    /** 日期格式器 */
    public static final String DATE_FORMAT_MONTH_DAY = "MM-dd";
    /** 日期格式器 */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /** 日期格式器 */
    public static final String DATE_FORMAT_HH = DATE_FORMAT + " HH";
    /** 日期格式器 */
    public static final String DATE_FORMAT_MM = DATE_FORMAT + " HH:mm";
    /** 日期格式器 MM-dd HH:mm */
    public static final String DATE_FORMAT_MM_HH = DATE_FORMAT_MONTH_DAY + " HH:mm";
    /** 时间格式器 */
    public static final String TIME_FORMAT = "HH:mm:ss";
    /** 日期时间格式器 */
    public static final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;
    /** 日期时间格式器 */
    public static final String DATETIME_FORMAT_1 = DATE_FORMAT + " 00:00:00";
    /** 日期戳 */
    public static final String DATE_STAMP = "yyyyMMdd";
    /** 时间戳 */
    public static final String TIME_STAMP = "HHmmssSSS";
    /** 时间戳（精确到秒） */
    public static final String TIME_STAMP_SECOND = "HHmmss";
    /** 日期时间戳 */
    public static final String DATETIME_STAMP = DATE_STAMP + TIME_STAMP;
    /** 日期时间戳（精确到秒） */
    public static final String DATETIME_STAMP_SECOND = DATE_STAMP + TIME_STAMP_SECOND;
    /** 天 */
    public static final int DATE_TYPE_DAY = 0;
    /** 月 */
    public static final int DATE_TYPE_MONTH = 1;
    /** 年 */
    public static final int DATE_TYPE_YEAR = 2;
    /** 一天的分钟数据 */
    public static final long ONE_DAY_MINUTE = 1440;
    /** 一小时的分钟数据 */
    public static final long ONE_HOUR_MINUTE = 60;
    /** 天 */
    public static final String DAY_STR = "天";
    /** 小时 */
    public static final String HOUR_STR = "小时";
    /** 分钟 */
    public static final String MINUTE_STR = "分钟";

    /** 将字符串解析成yyyy-MM-dd 的日期 */
    public static Date parseDate(String value) {
        try {
            return new SimpleDateFormat(DATE_FORMAT, Locale.CHINA).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    /** 将字符串解析成HH:mm:ss的时间 */
    public static Date parseTime(String value) {
        try {
            return new SimpleDateFormat(TIME_FORMAT, Locale.CHINA).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    /** 将字符串解析成yyyy-MM-dd HH:mm:ss的日期时间 */
    public static Date parseDateTime(String value) {
        try {
            return new SimpleDateFormat(DATETIME_FORMAT, Locale.CHINA).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    /** 将字符串解析成yyyyMMddHHmmssSSS的日期时间 */
    public static Date parseDateTimeStamp(String value) {
        try {
            return new SimpleDateFormat(DATETIME_STAMP, Locale.CHINA).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    /** 将字符串解析成yyyyMMddHHmmss的日期时间 */
    public static Date parseDateTimeSecond(String value) {
        try {
            return new SimpleDateFormat(DATETIME_STAMP_SECOND, Locale.CHINA).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    /** 将时间解析成yyyy-MM-dd的字符串 */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_FORMAT, Locale.CHINA).format(date);
    }

    /** 将时间解析成yyyy的字符串 */
    public static String formatDateToYear(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_FORMAT_YEAR, Locale.CHINA).format(date);
    }

    /** 将日期解析成HH:mm:ss的字符串 */
    public static String formatTime(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(TIME_FORMAT, Locale.CHINA).format(date);
    }

    /** 将日期时间解析成yyyy-MM-dd HH:mm:ss的字符串 */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATETIME_FORMAT, Locale.CHINA).format(date);
    }

    /** 将日期解析成yyyyMMddHHmmssSSS的字符串 */
    public static String formatDateTimestamp(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATETIME_STAMP, Locale.CHINA).format(date);
    }

    /** 将日期解析成yyyyMMddHHmmss的字符串 */
    public static String formatDateTimeSecond(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATETIME_STAMP_SECOND, Locale.CHINA).format(date);
    }

    /** 将日期解析成yyyyMMdd的字符串 */
    public static String formatDatestamp(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_STAMP, Locale.CHINA).format(date);
    }

    /** 将日期解析成HHmmssSSS的字符串 */
    public static String formatTimestamp(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(TIME_STAMP, Locale.CHINA).format(date);
    }

    /** 将日期解析成yyyy-MM-dd HH的字符串 */
    public static String formatDateHH(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_FORMAT_HH, Locale.CHINA).format(date);
    }

    /** 将日期解析成yyyy-MM-dd HH:mm的字符串 */
    public static String formatDateMM(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_FORMAT_MM, Locale.CHINA).format(date);
    }

    /** 将日期解析成yyyy-MM的字符串 */
    public static String formatYearMonth(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_FORMAT_MONTH, Locale.CHINA).format(date);
    }

    /** 将日期解析成MM-dd的字符串 */
    public static String formatMonthDay(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_FORMAT_MONTH_DAY, Locale.CHINA).format(date);
    }

    /** 将日期解析成MM-dd HH:mm的字符串 */
    public static String formatMonthHH(Date date) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat(DATE_FORMAT_MM_HH, Locale.CHINA).format(date);
    }

    /** 获取当天的开始时间, 如:2014-02-24 00:00:00 */
    public static Date getBeginOfDate() {
        return getBeginOfDate(null);
    }

    /** 获取当天的结束时间, 如:2014-02-24 23:59:59 */
    public static Date getEndOfDate() {
        return getEndOfDate(null);
    }

    /** 获取指定日期的开始时间, 如:2014-02-24 00:00:00 */
    public static Date getBeginOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return setDayToBegin(calendar).getTime();
    }

    /** 获取指定日期的结束时间, 如:2014-02-24 23:59:59 */
    public static Date getEndOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return setDayToEnd(calendar).getTime();
    }

    /**
     * @return 获取当年天数
     */
    public static int getDaysInYear() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.isLeapYear(calendar.get(Calendar.YEAR)) ? PlatformConstant.LENGTH_366 : PlatformConstant.LENGTH_365;
    }

    /**
     * @param specifiedDate 指定的时间，即需要比较的时间
     * @param largeDate     到期的时间，即比较大的时间
     * @return 天数差
     * 计算指定时间与当前时间的天数差
     */
    public static int getDaysDifference(Date specifiedDate, Date largeDate) {
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(largeDate);
        cReturnDate.setTime(specifiedDate);
        setDayToBegin(cNow);
        setDayToBegin(cReturnDate);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;
        return (int) (intervalMs / (PlatformConstant.LENGTH_1000 * PlatformConstant.LENGTH_86400));
    }

    /**
     * @param format   日期格式
     * @param variable 正数代表时间之后，负数代表时间之前
     * @param field    单位，年，月，日
     * @return 获取当前时间之前/之后多少年/月/日的时间
     */
    public static String getDiffDate(String format, int variable, int field) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        c.set(field, c.get(field) + variable);
        return formatter.format(c.getTime());
    }

    /**
     * 时间范围
     */
    public static class DateRange {
        /** 开始时间 */
        public Date begin;
        /** 结束时间 */
        public Date end;

        public DateRange() {
        }

        public DateRange(Date begin, Date end) {
            this.begin = begin;
            this.end = end;
        }

        public boolean inRange(Date date) {
            return date.compareTo(begin) >= 0 && date.compareTo(end) <= 0;
        }
    }

    /**
     * 获取指定日期的当天时间范围如(2014-06-09 00:00:00 2014-06-09 23:59:59)
     *
     * @param date 时间,为空表示当前时间
     * @return 时间范围
     */
    public static DateRange getDayRange(Date date) {
        DateRange range = new DateRange();
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        range.begin = setDayToBegin(calendar).getTime();
        range.end = setDayToEnd(calendar).getTime();

        return range;
    }

    /**
     * 获取指定日期本周的时间范围如
     *
     * @param date 时间,为空表示当前时间
     * @return 时间范围
     */
    public static DateRange getWeekReange(Date date) {
        DateRange range = new DateRange();
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        range.begin = setDayToBegin(calendar).getTime();

        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + PlatformConstant.LENGTH_6);
        range.end = setDayToEnd(calendar).getTime();

        return range;
    }

    /**
     * 获取指定日期本月的时间范围如
     *
     * @param date 时间,为空表示当前时间
     * @return 时间范围
     */
    public static DateRange getMonthReange(Date date) {
        DateRange range = new DateRange();
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        range.begin = setDayToBegin(calendar).getTime();

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        range.end = setDayToEnd(calendar).getTime();

        return range;
    }

    /**
     * 获取指定日期本季度的时间范围如
     *
     * @param date 时间,为空表示当前时间
     * @return 时间范围
     */
    public static DateRange getSeasonReange(Date date) {
        DateRange range = new DateRange();
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - (month % PlatformConstant.LENGTH_3));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        range.begin = setDayToBegin(calendar).getTime();

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + PlatformConstant.LENGTH_3);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        range.end = setDayToEnd(calendar).getTime();

        return range;
    }

    /**
     * 获取指定日期本半年的时间范围如
     *
     * @param date 时间,为空表示当前时间
     * @return 时间范围
     */
    public static DateRange getHalfYearReange(Date date) {
        DateRange range = new DateRange();
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int current = calendar.get(Calendar.MONTH) + 1;
        int month = Calendar.JANUARY;
        if (current > PlatformConstant.LENGTH_6) {
            month = Calendar.JULY;
        }
        calendar.set(calendar.get(Calendar.YEAR), month, 1, 0, 0, 0);
        range.begin = calendar.getTime();

        calendar.set(Calendar.MONTH, month + PlatformConstant.LENGTH_6);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        range.end = setDayToEnd(calendar).getTime();

        return range;
    }

    /**
     * 获取指定日期本半年的时间范围如
     *
     * @param date 时间,为空表示当前时间
     * @return 时间范围
     */
    public static DateRange getYearReange(Date date) {
        DateRange range = new DateRange();
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
        range.begin = calendar.getTime();

        calendar.set(Calendar.MONTH, PlatformConstant.LENGTH_12);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        range.end = setDayToEnd(calendar).getTime();

        return range;
    }

    public static Calendar setDayToEnd(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, PlatformConstant.LENGTH_23);
        calendar.set(Calendar.MINUTE, PlatformConstant.LENGTH_59);
        calendar.set(Calendar.SECOND, PlatformConstant.LENGTH_59);
        return calendar;
    }

    public static Calendar setDayToBegin(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    /**
     * 根据分钟得到X天X小时X分钟信息
     *
     * @param minutes 分钟
     */
    public static String getTimeInfoByMinute(long minutes) {
        StringBuilder info = new StringBuilder();
        if (minutes >= ONE_DAY_MINUTE) {
            info.append(handDayByMinute(minutes));
        } else if (minutes > ONE_HOUR_MINUTE) {
            info.append(handHourByMinute(minutes));
        } else {
            info.append(minutes).append(MINUTE_STR);
        }

        return info.toString();
    }

    /**
     * 通过分钟处理得到多少天
     *
     * @param minutes 分钟
     * @return 天
     */
    private static String handDayByMinute(long minutes) {
        StringBuilder info = new StringBuilder();
        long day = minutes / ONE_DAY_MINUTE;
        info.append(day).append(DAY_STR);
        long dayOther = minutes % ONE_DAY_MINUTE;
        if (dayOther > 0) {
            if (dayOther > ONE_HOUR_MINUTE) {
                info.append(handHourByMinute(dayOther));
            } else {
                info.append(dayOther).append(MINUTE_STR);
            }
        }

        return info.toString();
    }

    /**
     * 通过分钟处理得到多少小时
     *
     * @param minutes 分钟
     */
    private static String handHourByMinute(long minutes) {
        StringBuilder info = new StringBuilder();
        long hour = minutes / ONE_HOUR_MINUTE;
        info.append(hour).append(HOUR_STR);
        long hourOther = minutes % ONE_HOUR_MINUTE;
        if (hourOther > 0) {
            info.append(hourOther).append(MINUTE_STR);
        }

        return info.toString();
    }

    /**
     * 判断时间date1是否在时间date2之前
     *
     * @param date1 时间
     * @param date2 时间
     */
    public static boolean isDateBefore(String date1, String date2) {
        return DateUtils.parseDateTime(date1).before(DateUtils.parseDateTime(date2));
    }

    /**
     * 验证当前时间是否在指定时间范围内(例如9：00-22：30)
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return bool
     */
    public static boolean isRangeTime(String startTime, String endTime) {
        boolean isRangeTime = false;
        String nowDay = DateUtils.formatDate(new Date());
        String start = startTime, end = endTime;
        start = nowDay + " " + start + ":00";
        end = nowDay + " " + end + ":00";
        // 开始时间不在结束时间前，表示时间进行了跨天设置
        if (!isDateBefore(start, end)) {
            // 跨天设置的时间需要当前时间大于结束时间并且小于开始时间
            if (isDateBefore(DateUtils.formatDateTime(new Date()), end) || isDateBefore(start, DateUtils.formatDateTime(new Date()))) {
                isRangeTime = true;
            }
        } else {
            if (isDateBefore(DateUtils.formatDateTime(new Date()), end) && isDateBefore(start, DateUtils.formatDateTime(new Date()))) {
                isRangeTime = true;
            }
        }

        return isRangeTime;
    }

    /**
     * 判断时间是否属于范围内
     *
     * @param rangeTime 待检测的时间
     * @param startTime 范围开始时间
     * @param endTime   范围结束时间
     * @return bool
     */
    public static boolean isInRange(Date rangeTime, Date startTime, Date endTime) {
        return rangeTime.compareTo(startTime) >= 0 && rangeTime.compareTo(endTime) <= 0;
    }
}