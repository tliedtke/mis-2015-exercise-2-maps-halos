package com.example.mmbuw.hellomaps;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Tobias Liedtke on 09.05.2015.
 */
public class DrawCircleOnMap {
    public static Circle draw(GoogleMap mMap,Circle circle,LatLng position){
        //LatLng position = new LatLng(54, 12);

        //mMap.clear();
        float zoom = mMap.getCameraPosition().zoom;
        Log.v("zoom", String.valueOf(zoom));
        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        LatLng borderPointView = null;
        double distLngView = 0;
        LatLng corner1 = bounds.northeast;
        LatLng corner2 = bounds.southwest;
        /*if (myRest.longitude >= bounds.northeast.longitude) {
            corner = bounds.northeast;
        } else if (myRest.longitude <= bounds.southwest.longitude) {
            corner = bounds.southwest;
        }*/

        Vektor2D vektor1 = new Vektor2D(position.longitude - bounds.getCenter().longitude, position.latitude - bounds.getCenter().latitude);
        Vektor2D vektor2 = new Vektor2D(corner1.longitude - bounds.getCenter().longitude, corner1.latitude - bounds.getCenter().latitude);
        Vektor2D vektor3 = new Vektor2D(bounds.getCenter().longitude - corner1.longitude, bounds.getCenter().latitude - corner2.latitude);
        double denominator1 = Math.sqrt((vektor1.x * vektor1.x) + (vektor1.y * vektor1.y)) * Math.sqrt((vektor2.x * vektor2.x) + (vektor2.y * vektor2.y));
        double denominator2 = Math.sqrt((vektor1.x * vektor1.x) + (vektor1.y * vektor1.y)) * Math.sqrt((vektor3.x * vektor3.x) + (vektor3.y * vektor3.y));
        double angle1 = ((vektor1.x * vektor2.x) + (vektor1.y * vektor2.y)) / denominator1;
        double angle2 = ((vektor1.x * vektor3.x) + (vektor1.y * vektor3.y)) / denominator2;
        Log.v("angle1", String.valueOf(angle1));
        Log.v("angle2", String.valueOf(angle2));
        if (angle1 <= 0 && angle2 <= 0) {
            Log.v("test", "1");
            LatLng corner = bounds.southwest;
            double distLatView = corner.latitude - bounds.getCenter().latitude;
            double distLatPoint = position.latitude - bounds.getCenter().latitude;
            double distLngPoint = position.longitude - bounds.getCenter().longitude;
            distLngView = distLatView * distLngPoint / distLatPoint;

            borderPointView = new LatLng(corner.latitude, bounds.getCenter().longitude + distLngView);
        } else if (angle1 >= 0 && angle2 <= 0) {
            Log.v("test", "2");
            LatLng corner = bounds.northeast;
            double distLatView = corner.longitude - bounds.getCenter().longitude;
            double distLatPoint = position.longitude - bounds.getCenter().longitude;
            double distLngPoint = position.latitude - bounds.getCenter().latitude;
            distLngView = distLatView * distLngPoint / distLatPoint;

            borderPointView = new LatLng(bounds.getCenter().latitude + distLngView, corner.longitude);
        } else if (angle1 <= 0 && angle2 >= 0) {
            Log.v("test", "3");
            LatLng corner = bounds.southwest;
            double distLatView = corner.longitude - bounds.getCenter().longitude;
            double distLatPoint = position.longitude - bounds.getCenter().longitude;
            double distLngPoint = position.latitude - bounds.getCenter().latitude;
            distLngView = distLatView * distLngPoint / distLatPoint;

            borderPointView = new LatLng(bounds.getCenter().latitude + distLngView, corner.longitude);
        } else if (angle1 >= 0 && angle2 >= 0) {
            Log.v("test", "4");
            LatLng corner = bounds.northeast;
            double distLatView = corner.latitude - bounds.getCenter().latitude;
            double distLatPoint = position.latitude - bounds.getCenter().latitude;
            double distLngPoint = position.longitude - bounds.getCenter().longitude;
            distLngView = distLatView * distLngPoint / distLatPoint;

            borderPointView = new LatLng(corner.latitude, bounds.getCenter().longitude + distLngView);
        } else {
            Log.v("test", "5");
        }
       /* if (borderPointView != null) {
            if (circleboarder == null) {
                circleboarder = mMap.addCircle(new CircleOptions()
                        .center(borderPointView)
                        .radius(5000)
                        .strokeColor(Color.BLUE));
            } else {

                circleboarder.setCenter(borderPointView);
                circleboarder.setStrokeColor(Color.BLUE);
            }

        }*/
        Log.w("NE:",bounds.northeast.toString());
        Log.w("SW:", bounds.southwest.toString());
        Log.w("PE:", position.toString());
        double distDefault = calcDist(bounds.getCenter(), bounds.northeast) * 0.5;
        double dist2 = calcDist(bounds.getCenter(), position);
        double disttmp =0;



        if (borderPointView != null) {
            disttmp = calcDist(borderPointView, position);
        }


        double distView = calcDist(bounds.northeast, bounds.southwest);
        double dist = 0;
        if (!(position.latitude < bounds.northeast.latitude && position.latitude > bounds.southwest.latitude
                && position.longitude < bounds.northeast.longitude && position.longitude > bounds.southwest.longitude)) {
            dist = disttmp + (distView*0.05);// - distDefault;
            Log.v("dist :", String.valueOf(dist));
            Log.v("dist2:", String.valueOf(disttmp));
        }
    /*    if (circleinner == null) {
            circleinner = mMap.addCircle(new CircleOptions()
                    .center(bounds.getCenter())
                    .radius(distDefault)
                    .strokeColor(Color.BLACK));
        } else {
            //    circleinner.setVisible(true);
            circleinner.setRadius(distDefault);
            circleinner.setCenter(bounds.getCenter());
        }*/
        if (circle == null) {
            circle = mMap.addCircle(new CircleOptions()
                    .center(position)
                    .radius(dist)
                    .strokeColor(Color.RED));
        } else {

            circle.setRadius(dist);
            circle.setCenter(position);
        }
        if (dist > 0) {
            circle.setVisible(true);
        } else {
            if (circle != null)
                circle.setVisible(false);
            // circleinner.setVisible(false);
        }
        return circle;
    }
    //distance calc from http://www.movable-type.co.uk/scripts/latlong.html
    public static double calcDist(LatLng first, LatLng second) {
        double earthRadius = 6371000;
        double phiFirstLat = Math.toRadians(first.latitude);
        double phiSecondLat = Math.toRadians(second.latitude);
        double deltaLat = Math.toRadians(second.latitude - first.latitude);
        double deltaLng = Math.toRadians(second.longitude - first.longitude);
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(phiFirstLat) * Math.cos(phiSecondLat) * Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);
        double angularDistance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = (float) (earthRadius * angularDistance);

        return distance;
    }
}
