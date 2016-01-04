package com.jsvirzi.hideandseek;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by jsvirzi on 1/3/16.
 */
public class MyLocationListener implements LocationListener {

    MainActivity mContext;

    MyLocationListener(MainActivity context) {
        super();
        mContext = context;
    }

    @Override
    public void onLocationChanged(Location loc) {

        loc.getLatitude();
        loc.getLongitude();

        String Text = "My current location is: " + "Latitud = " + loc.getLatitude() + "Longitud = " + loc.getLongitude();

        Toast.makeText(mContext, Text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText( mContext, "GPS Disabled", Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText( mContext, "GPS Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}/* End of Class MyLocationListener */
