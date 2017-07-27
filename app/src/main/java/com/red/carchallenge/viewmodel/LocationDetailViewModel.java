package com.red.carchallenge.viewmodel;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.red.carchallenge.R;
import com.red.carchallenge.model.LocationResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import rx.subjects.BehaviorSubject;

public class LocationDetailViewModel {

    private LocationResult locationResult;
    private BehaviorSubject<GoogleMap> mapSubject = BehaviorSubject.create();


    public LocationDetailViewModel(LocationResult locationResult) {
        this.locationResult = locationResult;
    }

    public int getId() {
        return locationResult.getId();
    }

    public String getName() {
        return locationResult.getName();
    }

    public double getLatitude() {
        return locationResult.getLatitude();
    }

    public double getLongitude() {
        return locationResult.getLongitude();
    }

    public String getAddress() {
        return locationResult.getAddress();
    }

    public String getArrivalTime() {
        return locationResult.getArrivalTime();
    }


    /**
     * Methods to format date
     */

    public String getFormattedArrivalTime(Context context) {
        // Format from API: 2017-07-26T05:33:29.17e
        DateFormat arrivalFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS", Locale.US);
        arrivalFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        DateFormat currentFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        TimeZone tz = TimeZone.getTimeZone("America/Chicago");
        Date currentTime = Calendar.getInstance().getTime();
        currentFormatter.setTimeZone(tz);

        try {
            Date arrivalTime = arrivalFormatter.parse(getArrivalTime());

            long diff = arrivalTime.getTime() - currentTime.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;

            if (hours >= 1) {
                return String.format("%d %s %d %s", hours, getHoursText(context, hours), minutes - (hours * 60), getMinutesText(context, minutes));
            } else {
                return String.format("%d %s", minutes, getMinutesText(context, minutes));
            }

        } catch (ParseException pe) {
            return context.getResources().getString(R.string.arrival_error);
        }

    }

    private String getHoursText(Context context, long quantity) {
        return context.getResources().getQuantityString(R.plurals.hours, (int) quantity);
    }

    private String getMinutesText(Context context, long quantity) {
        return context.getResources().getQuantityString(R.plurals.minutes, (int) quantity);
    }

}
