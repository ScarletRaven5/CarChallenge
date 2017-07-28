package com.red.carchallenge.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    public static long getCurrentTimeInMillis() {
        DateFormat currentFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        // Using user's default timezone
        currentFormatter.setTimeZone(TimeZone.getDefault());

        return Calendar.getInstance().getTime().getTime();
    }

    public static long getArrivalTimeInMillis(String locationArrivalTime) {
        DateFormat arrivalFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
        // Assuming API's timezone is in UTC
        arrivalFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            long huh = arrivalFormatter.parse(locationArrivalTime).getTime();
            return huh;
        } catch (ParseException e) {
            return -1;
        }
    }
}
