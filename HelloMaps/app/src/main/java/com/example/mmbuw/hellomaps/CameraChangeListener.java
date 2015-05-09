package com.example.mmbuw.hellomaps;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Tobias Liedtke on 09.05.2015.
 */
public class CameraChangeListener implements GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ArrayList<Circle> circleArrayList = new ArrayList<Circle>();
    private Circle circle;

    public CameraChangeListener(GoogleMap mMap, ArrayList<Circle> circleArrayList) {
        this.mMap = mMap;
        this.circleArrayList = circleArrayList;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LatLng myRest = new LatLng(54, 12);
        for(Circle c : circleArrayList){
            DrawCircleOnMap.draw(mMap,c,c.getCenter());
        }

        //mMap.clear();

    }
    public void add(Circle circle){
        circleArrayList.add(circle);
    }
    public void remove(int id){
        circleArrayList.remove(id);
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public ArrayList<Circle> getCircleArrayList() {
        return circleArrayList;
    }

    public void setCircleArrayList(ArrayList<Circle> circleArrayList) {
        this.circleArrayList = circleArrayList;
    }
}
