package com.example.t.petconnect;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Gets the latitude and longitude of the park and sets a marker on the map and show the zoomed in map
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // initializes all the variables
    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private Intent intent;
    private LatLng park;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * gets the googleMap and sets a marker at the park location and shows it on the screen
     * @param googleMap
     */
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // gets the intent and the latitude and longitude of the park
        intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        // initializes a park with its latitude and longitude
        park = new LatLng(latitude, longitude);
        // adds a marker pin on the park location
        mMap.addMarker(new MarkerOptions().position(park).title("Park"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(park));
        // zooms the camera in the park
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(park,12.0f));

    }
}
