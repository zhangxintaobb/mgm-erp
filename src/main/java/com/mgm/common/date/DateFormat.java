package com.mgm.common.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateFormat {

    public final static String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public final static String DATE_FORMAT_STR_NO_MS = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public final static String DATE_FORMAT_STR = "dd/MM/yyyy";

    public static String toPhpDateString(Date date) {
        // TODO ensure always UTC timezone
        return new SimpleDateFormat("yyyy-MM-dd KK:mm:ss").format(date);
    }

    public static String toSQLDateTimeString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date toDate(Date date) throws ParseException {
        java.text.DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(formatter.format(date));
    }

    public static Date yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date plus(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    public static Date parseDate(String dateString, String datePattern) throws ParseException {
        return DateUtils.parseDate(dateString, datePattern);
    }
}
