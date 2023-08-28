package com.mgm.common.date;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarHolder {

    private Calendar calendar;
    private final String TIMEZONE_UTC = "UTC";

    public CalendarHolder(long timestamp) {
        this.setByTimestamp(timestamp);
    }

    public CalendarHolder(long timestamp, TimeZone timeZone) {
        this.setByTimestamp(timestamp, timeZone);
    }

    public CalendarHolder setByTimestamp(long timestamp, TimeZone timeZone) {
        this.calendar = Calendar.getInstance(timeZone);
        this.calendar.setTimeInMillis(timestamp);
        return this;
    }

    public CalendarHolder setByTimestamp(long timestamp) {
        return setByTimestamp(timestamp, TimeZone.getTimeZone(TIMEZONE_UTC));
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getYear() {
        return this.calendar.get(Calendar.YEAR);
    }

    /**
     * Returns a number representing the month that contains or begins
     * with the instant in time represented by this {@code Date} object.
     * The value returned is between {@code 0} and {@code 11},
     * with the value {@code 0} representing January.
     *
     * @return  the month represented by this date.
     * @see     java.util.Calendar
     */
    public int getMonth() {
        return this.calendar.get(Calendar.MONTH);
    }

    /**
     * Returns the day of the month represented by this {@code Date} object.
     * The value returned is between {@code 1} and {@code 31}
     * representing the day of the month that contains or begins with the
     * instant in time represented by this {@code Date} object, as
     * interpreted in the local time zone.
     *
     * @return  the day of the month represented by this date.
     * @see     java.util.Calendar
     */
    public int getDayOfMonth() {
        return this.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour() {
        return this.calendar.get(Calendar.HOUR);
    }

    public int getMinute() {
        return this.calendar.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return this.calendar.get(Calendar.SECOND);
    }
}
