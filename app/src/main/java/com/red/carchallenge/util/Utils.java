package com.red.carchallenge.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    public static final int TIME_ERROR = 0;
    public static final int TIME_HOURS = 1;
    public static final int TIME_MINUTES = 2;

    /**
     * Method that returns the corrent time in milliseconds
     *
     * @return The current time in milliseconds
     */
    public static long getCurrentTimeInMillis() {
        DateFormat currentFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        // Using user's default timezone
        currentFormatter.setTimeZone(TimeZone.getDefault());

        return Calendar.getInstance().getTime().getTime();
    }

    /**
     * Method that converts API's formatted date to milliseconds
     *
     * @param locationArrivalTime The location's arrival time coming from the API
     * @return The arrival time in milliseconds
     */
    public static long getArrivalTimeInMillis(String locationArrivalTime) {
        DateFormat arrivalFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
        // Assuming API's timezone is in UTC
        arrivalFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            return arrivalFormatter.parse(locationArrivalTime).getTime();
        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * Method that states whether the time is over an hour, under and hour, or errored
     *
     * @param millis The total milliseconds until arrival
     * @return The category the amount of time falls into
     */
    public static int getTimeUntilArrivalCategory(long millis) {
        if (millis == -1) {
            return TIME_ERROR;
        } else {
            // (1000 millis per second) per (60 seconds per minute) per (60 minutes per hour)
            long hours = millis / 1000 / 60 / 60;

            if (hours >= 1) {
                return TIME_HOURS;
            } else {
                return TIME_MINUTES;
            }
        }
    }

}
