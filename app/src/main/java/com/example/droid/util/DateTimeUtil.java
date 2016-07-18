package com.example.droid.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;



/**
 * Created by ss on 2/3/2016.
 */
public class DateTimeUtil {

    private static final String strFormatDateTime = "MM/dd/yy HH:mm";
    private static final String strFormatDate = "MM/dd/yy";
    private static final String strFormatTime = "HH:mm";


    public static String formatDateTime(long longDate) {
        try {
            DateTime dateTime = new DateTime(longDate);
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(strFormatDateTime);
            dateTime.toString(dateTimeFormatter);
            return dateTime.toString(dateTimeFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatTime(long longDate) {
        try {
            DateTime dateTime = new DateTime(longDate);
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(strFormatTime);
            dateTime.toString(dateTimeFormatter);
            dateTime.toLocalTime();
            return dateTime.toString(dateTimeFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatDate(long longDate) {
        try {
            DateTime dateTime = new DateTime(longDate);
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(strFormatDate);
            dateTime.toString(dateTimeFormatter);
            return dateTime.toString(dateTimeFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



    public static String formatDateTimeText(long longDate) {
        try {
            DateTime dateTime = new DateTime(longDate);
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(strFormatTime);
            dateTime.toString(dateTimeFormatter);
            return dateTime.toString(dateTimeFormatter);
        } catch (Exception e) {
            return "";
        }
    }

    public static int timeDifferenceBetweenMobileAndServer(long longDate) {
        DateTime dateTimeMobileNow = DateTime.now();
        dateTimeMobileNow.withZone(DateTimeZone.UTC).toLocalDateTime();

        DateTime dateTimeFromServer = new DateTime(longDate);
        dateTimeFromServer.withZone(DateTimeZone.UTC).toLocalDateTime();

        if (dateTimeMobileNow.isBefore(dateTimeFromServer)) {
            Minutes minutes = Minutes.minutesBetween(dateTimeMobileNow, dateTimeFromServer);
            return minutes.getMinutes();
        }
        return 0;
    }

    public static boolean isLocalTimeAfterServerTime(long longDateFormServer) {
        DateTime dateTimeMobileNow = DateTime.now();
        dateTimeMobileNow.withZone(DateTimeZone.UTC).toLocalDateTime();

        DateTime dateTimeFromServer = new DateTime(longDateFormServer);
        dateTimeFromServer.withZone(DateTimeZone.UTC).toLocalDateTime();
        if (dateTimeMobileNow.isAfter(dateTimeFromServer)) {
            return true;
        } else {
            return false;
        }
    }

    public static int convertMillisecondsToMins(long millis) {
        int min = (int) (millis % (1000 * 60 * 60));
        return min;
    }

    public static int convertMillisecondsToHours(long millis) {
        int hours = (int) millis / (1000 * 60 * 60);
        return hours;
    }

    public static long convertMinutesToMillisecounds(int minutes) {
        long milliseconds = minutes * 60000;
        return milliseconds;
    }

    public static boolean isExistWithinSpecificTimeRange(long startTime, long endTime, DateTime dateTimeNow, int delayTime) {

        DateTime tempStartDtm = new DateTime(startTime);
        DateTime startDateTimeNow = new DateTime().withTime(tempStartDtm.getHourOfDay(), tempStartDtm.getMinuteOfHour(), 0, 0).plusMinutes(delayTime).withZone(DateTimeZone.UTC).toDateTime();

        DateTime tempEndDtm = new DateTime(endTime);
        DateTime endDateTimeNow = new DateTime().withTime(tempEndDtm.getHourOfDay(), tempEndDtm.getMinuteOfHour(), 0, 0).withZone(DateTimeZone.UTC).toDateTime();

        if((dateTimeNow.isAfter(startDateTimeNow) || dateTimeNow.isEqual(startDateTimeNow)) && (dateTimeNow.isBefore(endDateTimeNow) || dateTimeNow.isEqual(endDateTimeNow) )){
            return true;
        }else {
            return false;
        }
    }

    public static ArrayList<String> formatHourAndMinuteFor2Digits(int hour, int minutes) {
        ArrayList<String> arrHourAndMinutes = new ArrayList<String>();
        int hourLength = String.valueOf(hour).length();
        int minuteLength = String.valueOf(minutes).length();
        String strHour, strMinute;
        if (hourLength == 1) {
            strHour = "0" + hour;
        } else {
            strHour = "" + hour;
        }

        if (minuteLength == 1) {
            strMinute = "0" + minutes;
        } else {
            strMinute = "" + minutes;
        }

        arrHourAndMinutes.add(strHour);
        arrHourAndMinutes.add(strMinute);

        return arrHourAndMinutes;
    }



}
