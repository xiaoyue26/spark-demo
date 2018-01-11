package com.xiaoyue26.www.utils;



import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by chenheng on 15/4/7.
 */
public class TimeUtils {

    public static final long MILLIS_PER_SECOND = 1000;
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;
    public static final int YEAR_START = 1900;
    public static final int MONTH_START = 1;

    private static ThreadLocal<DateFormat> dateParser = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static ThreadLocal<DateFormat> timeParser = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static long getCurrentTimestamp() {
        return new Date().getTime();
    }

    public static long getTimestamp(int year, int month, int day, int hour, int minute, int second) {
        Date date = new Date(year - YEAR_START, month - MONTH_START, day, hour, minute, second);
        return date.getTime();
    }


    public static long toTimestamp(String time) {
        if (time.contains("/")) {
            time = lockTimeToStandardTime(time);
        }
        String date = time.split(" ")[0];
        String watch = time.split(" ")[1];
        int year = Integer.valueOf(date.split("-")[0]);
        int month = Integer.valueOf(date.split("-")[1]);
        int day = Integer.valueOf(date.split("-")[2]);
        int hour = Integer.valueOf(watch.split(":")[0]);
        int min = Integer.valueOf(watch.split(":")[1]);
        int second = Integer.valueOf(watch.split(":")[2]);
        return getTimestamp(year, month, day, hour, min, second);
    }

    public static String lockTimeToStandardTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter2.format(formatter.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static int getYear(long timestamp) {
        return Integer.valueOf(parseDate(timestamp).split("-")[0]);
    }

    public static int getMonth(long timestamp) {
        return Integer.valueOf(parseDate(timestamp).split("-")[1]);
    }

    public static int getDay(long timestamp) {
        return Integer.valueOf(parseDate(timestamp).split("-")[2]);
    }

    public static int getHour(long timestamp) {
        return Integer.valueOf(timestampToDateTime(timestamp).split(" ")[1].split(":")[0]);
    }

    public static int getMinute(long timestamp) {
        return Integer.valueOf(timestampToDateTime(timestamp).split(" ")[1].split(":")[1]);
    }

    public static int getSecond(long timestamp) {
        return Integer.valueOf(timestampToDateTime(timestamp).split(" ")[1].split(":")[2]);
    }

    public static String encodeDateTime(String dateTime) {
        return dateTime.replace(' ', '_');
    }

    public static String decodeDateTime(String dateTime) {
        return dateTime.replace('_', ' ');
    }

    /**
     * @param ts timestamp
     * @return yyyy-mm-dd_hh:mm:ss
     */
    public static String timestampToEncodedDateTime(long ts) {
        return encodeDateTime(timestampToDateTime(ts));
    }

    /**
     * @param dateTime yyyy-mm-dd_hh:mm:ss
     * @return timestamp in mills
     */
    public static long encodedDateTimeToTimestamp(String dateTime) {
        return toTimestamp(decodeDateTime(dateTime));
    }

    public static String parseDate(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        try {
            return dateParser.get().format(ts);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param timestamp mills
     * @return yyyy-mm-dd hh:mm:ss
     */
    public static String timestampToDateTime(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        try {
            return timeParser.get().format(ts);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean isDateTime(String str) {
        return Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d").matcher(str).find();
    }

    public static boolean isTimestampInMills(String str) {
        return Pattern.compile("\\d{13}").matcher(str).find();
    }

    public static boolean isTimestampInSecond(String str) {
        return Pattern.compile("\\d{10}").matcher(str).find();
    }


    public static String convertDate2str(Date date) {
        try {
            return dateParser.get().format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String lastNDay(int n) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -n);
        Date date = cal.getTime();
        return TimeUtils.convertDate2str(date);
    }

}
