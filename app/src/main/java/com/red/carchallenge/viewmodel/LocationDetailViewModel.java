package com.red.carchallenge.viewmodel;

import com.red.carchallenge.model.LocationResult;
import com.red.carchallenge.util.Utils;

public class LocationDetailViewModel extends BaseViewModel {

    private LocationResult locationResult;

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

    public long getTimeUntilArrivalInMillis() {
        return Utils.getArrivalTimeInMillis(getArrivalTime()) - Utils.getCurrentTimeInMillis();
    }

}
