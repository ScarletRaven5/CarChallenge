package com.red.carchallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LocationResult extends BaseModel implements Parcelable {

    @SerializedName("ID")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("Address")
    private String address;
    @SerializedName("ArrivalTime")
    private String arrivalTime;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    private LocationResult(Parcel in) {
        id = in.readInt();
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
        arrivalTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(address);
        dest.writeString(arrivalTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LocationResult> CREATOR = new Creator<LocationResult>() {
        @Override
        public LocationResult createFromParcel(Parcel in) {
            return new LocationResult(in);
        }

        @Override
        public LocationResult[] newArray(int size) {
            return new LocationResult[size];
        }
    };

}
