package com.red.carchallenge.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.red.carchallenge.App;
import com.red.carchallenge.R;
import com.red.carchallenge.injection.DaggerLocationDetailActivityComponent;
import com.red.carchallenge.injection.LocationDetailActivityComponent;
import com.red.carchallenge.injection.LocationDetailActivityModule;
import com.red.carchallenge.model.LocationResult;
import com.red.carchallenge.viewmodel.LocationDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

public class LocationDetailActivity extends AppCompatActivity {

    public static final String ARG = "LOCATION";
    public static final String MAP = "MAP";

    @BindView(R.id.image_map)
    MapView mapImage;
    @BindView(R.id.text_arrival_time)
    TextView arrivalTimeText;
    @BindView(R.id.text_title)
    TextView titleText;
    @BindView(R.id.text_address)
    TextView addressText;
    @BindView(R.id.text_latitude)
    TextView latitudeText;
    @BindView(R.id.text_longitude)
    TextView longitudeText;

    private LocationResult locationResult;
    private LocationDetailViewModel viewModel;
    private CompositeSubscription subscriptions = Subscriptions.from();
    private BehaviorSubject<GoogleMap> mapSubject = BehaviorSubject.create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        ButterKnife.bind(this);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MAP");
        }
        mapImage.onCreate(mapViewBundle);

        LocationDetailActivityComponent component = DaggerLocationDetailActivityComponent
                .builder()
                .locationDetailActivityModule(new LocationDetailActivityModule(this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        component.injectLocationDetailActivity(this);

        locationResult = getIntent().getParcelableExtra(ARG);
        viewModel = new LocationDetailViewModel(locationResult);
        setTitle(locationResult.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }


    /**
     * According to MapView documentation:
     * "Users of this class must forward all the life cycle methods from the Activity or Fragment
     * containing this view to the corresponding ones in this class."
     */
    @Override
    public void onResume() {
        super.onResume();
        mapImage.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapImage.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapImage.onDestroy();
        // unsubscribe from Observable to prevent memory leaks from leaked context
        subscriptions.unsubscribe();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP, mapViewBundle);
        }
        mapImage.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapImage.onLowMemory();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void init() {
        initViews();
        initSubscriptions();
    }

    private void initViews() {
        arrivalTimeText.setText(viewModel.getFormattedArrivalTime(getApplicationContext()));
        titleText.setText(viewModel.getName());
        addressText.setText(viewModel.getAddress());
        latitudeText.setText(String.format("%.2f", viewModel.getLatitude()));
        longitudeText.setText(String.format("%.2f", viewModel.getLongitude()));
    }

    private void initSubscriptions() {
        subscriptions.addAll(
                subscribeToMapReadyObservable(),
                subscribeToMap());
    }

    private Subscription subscribeToMapReadyObservable() {
        return Observable.create(new Observable.OnSubscribe<GoogleMap>() {
            @Override
            public void call(final Subscriber<? super GoogleMap> subscriber) {
                getMap(subscriber);
            }
        }).subscribe(mapSubject);
    }

    private Subscription subscribeToMap() {
        return mapSubject.subscribe(this::addMarkerToMap);
    }

    private void getMap(final Subscriber<? super GoogleMap> subscriber) {
        OnMapReadyCallback mapReadyCallback = subscriber::onNext;
        mapImage.getMapAsync(mapReadyCallback);
    }

    private void addMarkerToMap(GoogleMap map) {
        LatLng position = new LatLng(locationResult.getLatitude(), locationResult.getLongitude());
        map.addMarker(new MarkerOptions().position(position));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 18));
    }

}
