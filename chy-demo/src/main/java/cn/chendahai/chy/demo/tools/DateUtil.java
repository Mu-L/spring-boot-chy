package cn.chendahai.chy.demo.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author Penggq
 * @since 2019/8/15 14:46
 */
public class DateUtil {

    public enum Pattern {

        /**
         * 默认格式化模式，将秒转化为 x hours and y minutes
         */
        DEFAULT,
        ;

    }

    public static String getTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String formatDateYMD(Date date) {
        return formatDate(date, "+0000", "yyyy-MM-dd");
    }

    public static String formatDateYMD(Date date, String country) {
        return formatDate(date, getCountryZone(country), "yyyy-MM-dd");
    }

    public static String formatDateYM(Date date, String country) {
        return formatDate(date, getCountryZone(country), "yyyy-MM");
    }

    public static String formatDateY(Date date, String country) {
        return formatDate(date, getCountryZone(country), "yyyy");
    }

    public static String formatDateByFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.UK);
        return sdf.format(date);
    }

    public static String formatDate(Date date, String zone) {
        return formatDate(date, zone, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String zone, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT" + zone));
        return sdf.format(date);
    }

    public static String getDateStrByZone(String country) {
        try {
            return DateUtil.formatDate(new Date(), DateUtil.getCountryZone(country), "yyyyMMdd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "19700101";
    }

    public static String formatDate(long timestamp) {
        return formatDate(new Date(timestamp));
    }

    public static Date parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }

    public static Date parseDateDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }

    public static Date parseDate(String date, String zone) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        try {
            return sdf.parse(date + zone);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }

    public static Date parseDate(String date, String zone, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format + "Z", Locale.UK);
        try {
            return sdf.parse(date + zone);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }

    public static Date parseDateByCountry(String date, String country) {
        return parseDate(date, getCountryZone(country));
    }

    public static String getCountryZone(String country) {
        return "+0000";
    }


    public static Date getNext0Clock(String country) {

        Date nowDate = new Date();
        nowDate = addTime(nowDate, TimeUnit.DAYS.toMillis(1));

        String time = formatDate(nowDate, getCountryZone(country), "yyyy-MM-dd");
        return parseDateByCountry(time + " 00:00:00", country);
    }

    /**
     * 获取距下一天0点的秒数
     *
     * @param country
     * @return
     */
    public static int getNext0ClockSecond(String country) {

        long millis = getNext0Clock(country).getTime() - System.currentTimeMillis();
        return (int) (millis / 1000);
    }

    /**
     * 获取下一个6点
     */
    public static Date getNext6Clock(String country) {

        // 初始时间加18小时
        Date initDate = addTime(new Date(), TimeUnit.HOURS.toMillis(18));

        String time = formatDate(initDate, getCountryZone(country), "yyyy-MM-dd");
        return addTime(parseDateByCountry(time + " 00:00:00", country), TimeUnit.HOURS.toMillis(6));
    }

    /**
     * 获取距下一天6点的秒数
     */
    public static int getNext6ClockSecond(String country) {

        long millis = getNext0Clock(country).getTime() - System.currentTimeMillis();
        return (int) (millis / 1000);
    }

    public static Date addTime(Date startDate, long millis) {
        return new Date(startDate.getTime() + millis);
    }

    /**
     * 清除field及field以下级别的域值
     */
    public static Calendar clearSubFields(Calendar c, int fields) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c.getTime());
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        switch (fields) {
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.MINUTE:
                calendar.clear(Calendar.MINUTE);
            case Calendar.SECOND:
                calendar.clear(Calendar.SECOND);
            case Calendar.MILLISECOND:
                calendar.clear(Calendar.MILLISECOND);
        }
//        clearCalendar(calendar, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
//        clearCalendar(calendar, fields);
        return calendar;
    }

    /**
     * 清除field及field以下级别的域值
     */
    public static Date clearSubFields(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar = clearSubFields(calendar, field);
        return calendar.getTime();
    }

    public static boolean sameDay(Date date1, Date date2, String country) {
        try {
            String zone = getCountryZone(country);
            return formatDate(date1, zone).equals(formatDate(date2, zone));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将秒格式化为指定格式
     *
     * @param second
     * @return
     */
    public static String formatSecond(long second) {
        return formatSecond(second, Pattern.DEFAULT);
    }

    /**
     * 将秒格式化为指定格式
     *
     * @param second
     * @param pattern
     * @return
     */
    public static String formatSecond(long second, Pattern pattern) {
        if (second < 0) {
            return null;
        } else if (second == 0) {
            return "0 second";
        }
        long hour;
        long minute;
        StringBuffer res = new StringBuffer();
        switch (pattern) {
            case DEFAULT:
                minute = (long) Math.ceil(second / 60.0);
                hour = minute / 60;
                minute %= 60;
                if (hour > 0) {
                    res.append(hour + " hour" + (hour > 1 ? "s" : "") + " and ");
                }
                res.append(minute + " minute" + (minute > 1 ? "s" : ""));
                return res.toString();
        }

        return second + " seconds";
    }

    /**
     * 计算两个时间戳相差天数
     * <p>
     * 计算时向下取整, 例如相差4.1天时返回4天
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDayInterval(Date startDate, Date endDate) {
        long l = endDate.getTime() - startDate.getTime();
        return (int) Math.floorDiv(l, TimeUnit.DAYS.toMillis(1));
    }

    /**
     * 根据国家，获取当前国家今天的0点
     *
     * @param country
     * @return
     */
    public static Date getToday0Clock(String country) {
        return getToday0Clock(country, new Date());
    }

    /**
     * 根据国家，获取当前国家nowDate那一天的0点
     *
     * @param country
     * @return
     */
    public static Date getToday0Clock(String country, Date nowDate) {

        String time = formatDate(nowDate, getCountryZone(country), "yyyy-MM-dd");
        return parseDateByCountry(time + " 00:00:00", country);
    }

    /**
     * 获取当前时间距凌晨12点的时间
     */
    public static long getLeftTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long leftTime = cal.getTimeInMillis() - System.currentTimeMillis();
        return leftTime / 1000;
    }

    /**
     * 获取num天后的0点0分0秒
     */
    public static Date getAddDays(Date startTime, Integer num, String country) {

        Date endDate = addTime(startTime, TimeUnit.DAYS.toMillis(num + 1));

        String time = formatDate(endDate, getCountryZone(country), "yyyy-MM-dd");
        return parseDateByCountry(time + " 00:00:00", country);
    }

    /**
     * 获取num天前的0点0分0秒
     *
     * @param startTime
     * @param num
     * @param country
     * @return
     */
    public static Date getMinusDays(Date startTime, Integer num, String country) {

        Date endDate = addTime(startTime, -TimeUnit.DAYS.toMillis(num));

        String time = formatDate(endDate, getCountryZone(country), "yyyy-MM-dd");
        return parseDateByCountry(time + " 00:00:00", country);
    }

    /*
    获取 num 天后 0时
     */
    public static Date getAddDays0Clock(Integer num) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, num);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * local时间转换成UTC时间
     *
     * @param localDate
     * @return
     */
    public static Date localToUTC(Date localDate) {
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }

    /**
     * 判断两个date类型是否同一天
     */
    public static boolean isSameDay(Date date1, Date date2, String country) {
        String zone = getCountryZone(country);
        return formatDate(date1, zone, "yyyy-MM-dd").equals(formatDate(date2, zone, "yyyy-MM-dd"));
    }

    /**
     * 判断date1是否是date2的前一天
     */
    public static boolean isBeforeDay(Date date1, Date date2, String country) {
        return isSameDay(addTime(date1, TimeUnit.DAYS.toMillis(1)), date2, country);
    }

    /**
     * 判断当前时间距离第二天凌晨的秒数
     *
     * @return 返回值单位为[s:秒]
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    /**
     * 根据传入的时间戳，将其时间限制在半年内
     *
     * @param timestamp 时间戳
     * @return 限制后的时间
     */
    public static Long limitHalfYearTime(Long timestamp) {

        if (timestamp == null) {
            return new Date().getTime();
        }
        if (timestamp < 0) {
            return new Date().getTime();
        }
        Date startDate = new Date(timestamp);

        // 创建 Calendar 对象，并设置为 nowDate 的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // 获取半年前的时间
        calendar.add(Calendar.MONTH, -6);
        Date sixMonthsAgo = calendar.getTime();

        // 如果 startDate 比半年前更早，则返回半年前的时间
        if (startDate.before(sixMonthsAgo)) {
            return sixMonthsAgo.getTime();
        } else {
            return timestamp;
        }
    }

    public static boolean dataIsValid(Date startTime, Date endTime, Date targetTime) {
        if (null == targetTime) {
            return false;
        }
        boolean valid = true;
        if (startTime != null) {
            valid = startTime.before(targetTime);
        }
        if (valid && endTime != null) {
            valid = endTime.after(targetTime);
        }
        return valid;
    }

}
