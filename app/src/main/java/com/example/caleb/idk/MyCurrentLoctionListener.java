package com.example.caleb.idk;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Caleb on 10/25/2015.
 */

public class MyCurrentLoctionListener implements android.location.LocationListener {

        double longatude;
        double latitude;

        @Override
        public void onLocationChanged(Location location) {
            location.getLatitude();
            location.getLongitude();
            longatude = location.getLongitude();
            latitude = location.getLatitude();
            String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

            //I make a log to see the results
            Log.e("MY CURRENT LOCATION", myLocation);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        public double getLong(){
            return longatude;
        }

    public double getLat(){
        return latitude;
    }

    }


