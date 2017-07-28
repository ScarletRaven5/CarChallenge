package com.red.carchallenge.viewmodel;

import com.google.android.gms.maps.GoogleMap;
import com.red.carchallenge.model.LocationResult;
import com.red.carchallenge.util.Utils;

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

    public long getTimeToArriveInMillis() {
        return Utils.getArrivalTimeInMillis(getArrivalTime()) - Utils.getCurrentTimeInMillis();
    }
}
