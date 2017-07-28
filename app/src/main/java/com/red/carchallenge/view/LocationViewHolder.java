package com.red.carchallenge.view;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.red.carchallenge.R;
import com.red.carchallenge.viewmodel.LocationViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("ViewConstructor")
public class LocationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name_location)
    TextView name;
    @BindView(R.id.address_location)
    TextView address;

    LocationViewModel location;

    public LocationViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(LocationViewModel location) {
        this.location = location;
        name.setText(location.getName());
        address.setText(location.getAddress());
    }

    @OnClick(R.id.layout_location)
    public void clickLocation() {
        location.onClickLocation(itemView);
    }
}