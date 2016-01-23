package com.jsvirzi.hideandseek;

import android.location.Location;
import android.location.LocationManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jsvirzi on 1/4/16.
 */
public class SMSSender {

    public String TAG = "SMSSender";
//    public MainActivity mContext;

    final SmsManager mSmsManager = SmsManager.getDefault();

//    SMSSender(MainActivity context) {
//        mContext = context;
//        if(mContext == null) Log.i(TAG, "setting context to null");
//        else Log.i(TAG, "setting context");
//    }
//
    public void sendMessage() {
        Log.i("Send SMS", "");
        // String phoneNo = mPhoneNumber.getText().toString();
        String phoneNo = "12017441790";
//        Location location = mContext.mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        String message = "Location: " + "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();
        String message = "Hello Joe";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
//            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
