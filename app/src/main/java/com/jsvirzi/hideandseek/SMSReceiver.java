package com.jsvirzi.hideandseek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jsvirzi on 1/3/16.
 */

public class SMSReceiver extends BroadcastReceiver {

    public String TAG = "SMSReceiver";

    @Override
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
                        String omessage = "No Location Information Available";
                        omessage = MainActivity.getGps();
                        Log.i(TAG, "Sending SMS = [" + omessage + "]");

                        MainActivity.setIncomingPhoneNumber(phoneNo);

                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNo, null, omessage, null, null);
                            Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }
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

}
