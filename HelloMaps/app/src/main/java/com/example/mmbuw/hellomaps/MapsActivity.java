package com.example.mmbuw.hellomaps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng myPos = new LatLng(54, 11);
    private ArrayList<Circle> circleArrayList = new ArrayList<Circle>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(54.0213, 12.3454)).title("MyPosition"));
        ArrayList<MarkerOptions> markerOptionses = new ArrayList<MarkerOptions>();
        markerOptionses.add(new MarkerOptions().position(new LatLng(54.032313, 12.4754)).title("Rest1"));
        markerOptionses.add(new MarkerOptions().position(new LatLng(54.157813, 12.3044)).title("Rest2"));
        markerOptionses.add(new MarkerOptions().position(new LatLng(54.02343, 12.2474)).title("Rest3"));

        for(MarkerOptions m : markerOptionses) {
            mMap.addMarker(m);
            Circle c = DrawCircleOnMap.draw(mMap,null,m.getPosition());
            circleArrayList.add(c);
        }


        //LatLngBounds Test = new LatLngBounds(
          //      new LatLng(53, 8), new LatLng(56, 12));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(54.0213, 12.3454), 12));
       // LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        mMap.setOnCameraChangeListener(new CameraChangeListener(mMap, circleArrayList));
    }


}
