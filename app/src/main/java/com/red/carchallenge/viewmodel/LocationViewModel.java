package com.red.carchallenge.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.red.carchallenge.model.LocationResult;
import com.red.carchallenge.view.activity.LocationDetailActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LocationViewModel extends BaseViewModel{
    private LocationResult locationResult;

    public LocationViewModel(LocationResult locationResult) {
        this.locationResult = locationResult;
    }

    public String getName() {
        return locationResult.getName();
    }

    public String getAddress() {
        return locationResult.getAddress();
    }

    public float getDistance(LatLng currentPosition) {
        float[] results = new float[1];
        Location.distanceBetween(currentPosition.latitude, currentPosition.longitude,
                locationResult.getLatitude(), locationResult.getLongitude(), results);
        return results[0];
    }

    /**
     * Method that uses location's arrival time and finds how far in the future it is from the
     * current time
     *
     * @return The difference between the future arrival time and current time
     */
    public long getArrivalTimeInMillis() {
        DateFormat arrivalFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS", Locale.US);
        arrivalFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date currentTime = Calendar.getInstance().getTime();
        TimeZone tz = TimeZone.getTimeZone("America/Chicago");
        DateFormat currentFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        currentFormatter.setTimeZone(tz);

        try {
            Date arrivalTime = arrivalFormatter.parse(locationResult.getArrivalTime());

            return arrivalTime.getTime() - currentTime.getTime();
        } catch (ParseException pe) {
            return 0;
        }
    }

    /**
     * Method to send the user to the location's detail page
     *
     * @param view The view housing the location to view
     */
    public void onClickLocation(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, LocationDetailActivity.class);
        intent.putExtra(LocationDetailActivity.ARG, locationResult);
        context.startActivity(intent);
    }

}
