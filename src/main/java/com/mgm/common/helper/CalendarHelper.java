package com.mgm.common.helper;

import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarHelper {

    public static int getDayOfMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        return calendar.getTime();
    }

    public static int getTotalDaysOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getMax(int type) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        return calendar.getActualMaximum(type);
    }

    public static int getPreviousMax(int type) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        if (type == Calendar.DAY_OF_MONTH) {
            calendar.add(Calendar.MONTH, -1);
        }
        return calendar.getActualMaximum(type);
    }

    public static int getMin(int type) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        return calendar.getActualMinimum(type);
    }

    public static int getCurrent(int type) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        return calendar.get(type);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        return calendar.get(Calendar.YEAR);
    }

    public static Date parse(String dateString, String format) {
        if (dateString == null) {
            return new Date(0);
        }
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (Exception e) {
            return new Date(0);
        }
    }

    /**
     * Adds or subtracts the specified amount of time to the given date, based on the calendar's rules. For example, to subtract 5 days from the given date, you can achieve it by calling:
     *     changeTime(date, Calendar.DAY_OF_MONTH, -5).
     *     Params:
     *     date - the original date to change from
     *     calendarType – the calendar field, e.g Calendar.DAY_OF_MONTH, Calendar.MONTH.
     *     amount – the amount of date or time to be added to the field.
     **/
    public static Date changeTime(Date date, int calendarType, int amount) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(date);
        calendar.add(calendarType, amount);
        return calendar.getTime();
    }

    public static String getFirstDayOfCurrentMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(calendar.getTime());
    }

    public static String getFirstDayOfNextMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(calendar.getTime());
    }
}
