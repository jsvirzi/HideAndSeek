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

public class SMSReceiver extends BroadcastReceiver {

    public String TAG = "SMSReceiver";

    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "Here I am");

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
//                    if(message == "SEND GPS") {
//                        mContext.sendSMSMessage();
//                        Log.i(TAG, "We are so doing this");
//                    }
                    Log.i(TAG, "senderNum: " + senderNum + "; message: " + message);
                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e(TAG, "Exception smsReceiver" +e);

        }
    }
}
