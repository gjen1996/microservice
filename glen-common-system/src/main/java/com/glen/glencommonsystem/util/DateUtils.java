package com.glen.glencommonsystem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;


/**
 * @author 盖玉成
 * @description 时间包工具类
 * @date 2020/9/3 10:59
 */
public class DateUtils {
    public static final String DATE_FORMART_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss SSS";

    /**
     * 指定日期加上天数后的日期
     */
    public static Date plusDay(int num, Date currentDate) {
        LocalDateTime localDateTime = dateConvertLocalDateTime(currentDate);
        localDateTime = localDateTime.plusDays(num);
        return localDateTimeConvertDate(localDateTime);
    }

    /**
     * @description 根据时间字符串转换成
     */
    public static Date dateStrConvertDate(String currentDate, String formateStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formateStr);
        LocalDateTime localDateTime = LocalDateTime.parse(currentDate, dateTimeFormatter);
        return localDateTimeConvertDate(localDateTime);
    }

    /**
     * @description 根据date转换成localDateTime
     */
    public static LocalDateTime dateConvertLocalDateTime(Date date) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }

    /**
     * @description 方法描述 根据localDateTime转换成date
     */
    public static Date localDateTimeConvertDate(LocalDateTime localDateTime) {
        return Date.from(getZonedDateTimeByLocalDateTime(localDateTime).toInstant());
    }

    /**
     * @description 根据时间转换成时间字符串
     */
    public static String formatDateToParse(Date currentDate, String formateStr) {
        LocalDateTime localDateTime = dateConvertLocalDateTime(currentDate);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formateStr);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * @description 根据开始时间，结束时间进行对比
     */
    public static boolean compareDay(Date startDate, Date endDate) {
        return startDate.before(endDate);
    }

    /**
     * @Description获取本月第一天
     */
    public static Date getMonthFirstDay() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime firstday = date.with(TemporalAdjusters.firstDayOfMonth());
        return localDateTimeConvertDate(firstday);
    }

    /**
     * @Description获取本月最后一天
     */
    public static Date getMonthLastDay() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime lastday = date.with(TemporalAdjusters.lastDayOfMonth());
        return localDateTimeConvertDate(lastday);
    }

    /**
     * @Description 根据localDateTime转换成ZonedDateTime对象，用于把localDatTime转成Date
     */
    public static ZonedDateTime getZonedDateTimeByLocalDateTime(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return zonedDateTime;
    }

    /**
     * @description 获得某天最大时间 2019-10-14 23:59:59
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        ;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeConvertDate(endOfDay);
    }

    /**
     * @description 获得某天最小时间 2019-10-14 00:00:00
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeConvertDate(startOfDay);
    }

    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static R formateStrToLong(String timestamp) {
        R r = new R();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        Long nowTime = getTimestampOfDateTime(localDateTimeNow);
        LocalDateTime tokenDateTime = dateConvertLocalDateTime(dateStrConvertDate(timestamp, "yyyy-MM-dd HH:mm:ss SSS"));
        Long exangeTime = getTimestampOfDateTime(tokenDateTime);
        r.put("nowTime", nowTime);
        r.put("exangeTime", exangeTime);
        return r;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss转为yyyy-MM-dd HH:mm:ss SSS
     */
    public static String secondChange(String time) {
        LocalDateTime localTime = dateConvertLocalDateTime(dateStrConvertDate(time, "yyyy-MM-dd HH:mm:ss"));
        Long exangeTime = getTimestampOfDateTime(localTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date date = new Date(exangeTime);
        String str = sdf.format(date);
        return str;
    }

    // 获取当前时间
    public static String currentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}