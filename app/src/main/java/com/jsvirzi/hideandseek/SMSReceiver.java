package com.jsvirzi.hideandseek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
                    MainActivity.mPhoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ MainActivity.mPhoneNumber + "; message: [" + message + "]");

                    if(message.equals("SEND GPS")) {

                        MainActivity.mCommand = message;

//                        Log.i(TAG, "sending sms");
//                        // String phoneNo = mPhoneNumber.getText().toString();
//                        String phoneNo = "14087077237";
//                        String omessage = "No Location Information Available";
//                        omessage = MainActivity.getGps();
//                        Log.i(TAG, "Sending SMS = [" + omessage + "]");
//
//                        MainActivity.setIncomingPhoneNumber(phoneNo);
//
//                        try {
//                            SmsManager smsManager = SmsManager.getDefault();
//                            smsManager.sendTextMessage(phoneNo, null, omessage, null, null);
//                            Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
//                        }
//
//                        catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    } else if(message.equals("CALL")) {
                        MainActivity.mCommand = "CALL";
//                        Intent callIntent = new Intent(Intent.ACTION_CALL);
//                        callIntent.setData(Uri.parse("tel:14087077237"));
//                        context.startActivity(callIntent);
                    }

                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: " + MainActivity.mPhoneNumber + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }

}
