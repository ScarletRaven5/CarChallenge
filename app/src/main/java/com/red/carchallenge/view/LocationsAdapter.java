package com.red.carchallenge.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.red.carchallenge.R;
import com.red.carchallenge.viewmodel.LocationViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    private List<LocationViewModel> locationList;

    public LocationsAdapter() {
        locationList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        holder.bind(locationList.get(position));
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    public void setItems(List<LocationViewModel> locationsList) {
        if (locationsList != null) {
            this.locationList = new ArrayList<>(locationsList);
        }
        sortByName();
    }

    public void sortByName() {
        Collections.sort(locationList, (loc1, loc2) ->
                loc1.getName().compareToIgnoreCase(loc2.getName()));
        notifyDataSetChanged();
    }

    public void sortByDistance(LatLng currentPosition) {
        Collections.sort(locationList, (loc1, loc2) ->
                Float.valueOf(loc1.getDistance(currentPosition)).compareTo(loc2.getDistance(currentPosition)));
        notifyDataSetChanged();
    }

    public void sortByTime() {
        Collections.sort(locationList, (loc1, loc2) ->
                Long.valueOf(loc1.getArrivalTimeInMillis()).compareTo(loc2.getArrivalTimeInMillis()));
        notifyDataSetChanged();
    }

}
