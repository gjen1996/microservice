package com.glen.product.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期 0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    /**
     * @return 次月1号0点
     */
    public static Date monthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        System.out.println(date);
        return date;
    }

    /**
     * <p>当前月份<br />
     * 月数周期为本月27号到下个月26号<br />
     * 算出27号0点
     * </p>
     *
     * @return Date yyyy-MM-dd HH:mm:ss
     * @author chenz76
     */
    public static Date getDateToTS() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);  //获取年份
        int month = calendar.get(Calendar.MONTH) + 1;   //国外是0-11月,所以要加一
        int day = calendar.get(Calendar.DAY_OF_MONTH);  //获取月的第几日
        String date = "";
        if (day < 27) {
            if (month < 10) {
                date = year + "-0" + month + "-27 " + "00:00:00";
            } else {
                date = year + "-" + month + "-27 " + "00:00:00";
            }
        } else {
            if ((month + 1) > 12) {
                date = (year + 1) + "-01-27 " + "00:00:00";
            } else {
                if (month < 10) {
                    date = year + "-0" + (month + 1) + "-27 " + "00:00:00";
                } else {
                    date = year + "-" + (month + 1) + "-27 " + "00:00:00";
                }
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(date, pos);
        return strtodate;
    }


    /**
     * 当前月份<br />
     * 月份的天数规则，本月27到下个月26
     *
     * @return String yyyy-MM
     * @author chenz76
     */
    public static String getDateToMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);  //获取年份
        int month = calendar.get(Calendar.MONTH) + 1;   //国外是0-11月,所以要加一
        int day = calendar.get(Calendar.DAY_OF_MONTH);  //获取月的第几日
        String date = "";
        if (day < 27) {
            if (month < 10) {
                date = year + "-0" + month;
            } else {
                date = year + "-" + month;
            }
        } else {
            if ((month + 1) > 12) {
                date = (year + 1) + "-01";
            } else {
                if (month < 10) {
                    date = year + "-0" + (month + 1);
                } else {
                    date = year + "-" + (month + 1);
                }
            }
        }
        return date;
    }

    /**
     * 上月月份<br />
     * 月份的天数规则，上月27到本月26
     *
     * @return String yyyy-MM
     * @author chenz76
     */
    public static String getDateToLastMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);  //获取年份
        int month = calendar.get(Calendar.MONTH) + 1;   //国外是0-11月,所以要加一
        int day = calendar.get(Calendar.DAY_OF_MONTH);  //获取月的第几日
        String date = "";
        if (day > 27) {
            if (month < 10) {
                date = year + "-0" + month;
            } else {
                date = year + "-" + month;
            }
        } else {
            if ((month - 1) < 1) {
                date = (year - 1) + "-12";
            } else {
                if (month < 10) {
                    date = year + "-0" + (month - 1);
                } else {
                    date = year + "-" + (month - 1);
                }
            }
        }
        return date;
    }

}
