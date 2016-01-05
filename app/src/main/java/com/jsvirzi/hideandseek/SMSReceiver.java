package com.jsvirzi.hideandseek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jsvirzi on 1/3/16.
 */

public class SMSReceiver extends BroadcastReceiver implements LocationListener {

    public String TAG = "SMSReceiver";
    public Location mLocation = null;
    public LocationManager mlocManager;

//    SMSReceiver() {
//        super();
//    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        String s = "New Location: " + "Latitude = " + mLocation.getLatitude() + "Longitude = " + mLocation.getLongitude();
        Log.i(TAG, s);
    }

    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras(); // Retrieves a map of extended data from the intent.

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: [" + message + "]");

                    if(message.equals("SEND GPS")) {

                        Log.i(TAG, "sending sms");
                        // String phoneNo = mPhoneNumber.getText().toString();
                        String phoneNo = "14087077237";
//        Location location = mContext.mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        String omessage = "No Location Information Available";
                        Log.i(TAG, "Sending SMS = [" + omessage + "]");
                        if(mLocation != null) {
                            omessage = "Location: " + "Latitude = " + mLocation.getLatitude() + " Longitude = " + mLocation.getLongitude();
                        }
                        Log.i(TAG, "Sending SMS = [" + omessage + "]");

                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNo, null, omessage, null, null);
//            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                        }

                        catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }




//                        if(mContext == null) Log.i(TAG, "are you an idiot?");
//                        else mContext.mSMSSender.sendMessage();
                        Log.i(TAG, "I did send it you know");
                    }

                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: " + senderNum + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "GPS disabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "GPS enabled");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}

//public class SMSReceiver extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(android.provider.Telephony.SMS_RECEIVED)) {
//            Bundle bundle = intent.getExtras();
//            if (bundle != null) {
//                // get sms objects
//                Object[] pdus = (Object[]) bundle.get("pdus");
//                if (pdus.length == 0) {
//                    return;
//                }
//                // large message might be broken into many
//                SmsMessage[] messages = new SmsMessage[pdus.length];
//                StringBuilder sb = new StringBuilder();
//                for (int i = 0; i < pdus.length; i++) {
//                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
//                    sb.append(messages[i].getMessageBody());
//                }
//                String sender = messages[0].getOriginatingAddress();
//                String message = sb.toString();
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                // prevent any other broadcast receivers from receiving broadcast
//                // abortBroadcast();
//            }
//        }
//    }
//
//}

//public class SMSReceiver extends BroadcastReceiver {
//
//    public String TAG = "SMSReceiver";
//
//    final SmsManager sms = SmsManager.getDefault();
//
//    public void onReceive(Context context, Intent intent) {
//
//        Log.i(TAG, "Here I am");
//
//        // Retrieves a map of extended data from the intent.
//        final Bundle bundle = intent.getExtras();
//
//        try {
//            if (bundle != null) {
//                final Object[] pdusObj = (Object[]) bundle.get("pdus");
//                for (int i = 0; i < pdusObj.length; i++) {
//                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//                    String senderNum = phoneNumber;
//                    String message = currentMessage.getDisplayMessageBody();
////                    if(message == "SEND GPS") {
////                        mContext.sendSMSMessage();
////                        Log.i(TAG, "We are so doing this");
////                    }
//                    Log.i(TAG, "senderNum: " + senderNum + "; message: " + message);
//                    // Show Alert
//                    int duration = Toast.LENGTH_LONG;
//                    Toast toast = Toast.makeText(context,
//                            "senderNum: "+ senderNum + ", message: " + message, duration);
//                    toast.show();
//
//                } // end for loop
//            } // bundle is null
//
//        } catch (Exception e) {
//            Log.e(TAG, "Exception smsReceiver" +e);
//
//        }
//    }
//}
