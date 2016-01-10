package com.jsvirzi.hideandseek;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraDevice;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SmsResultReceiver.Receiver {

    public static TextView mTextViewIncomingPhoneNumber;
    public static TextView mTextViewGpsLatitude;
    public static TextView mTextViewGpsLongitude;
    public static String mIncomingPhoneNumber;
    public static double mGpsLatitude;
    public static double mGpsLongitude;
    public static String mGpsString;
    public static String mCommand = null;

    private CameraDevice mCameraDevice;
    public EditText mPhoneNumber;
    public LocationManager mlocManager;
    private Button mSendButton;
    private Button mCallButton;
    public SMSSender mSMSSender;
    public Context mContext;
    public SmsResultReceiver mSmsResultReceiver;
    public Handler mHandler = null;

    public String TAG = "HideAndSeek";

    public static void setIncomingPhoneNumber(String s) {
        mIncomingPhoneNumber = s;
        mTextViewIncomingPhoneNumber.setText(mIncomingPhoneNumber);
    }

    public static void setGpsLatitude(double latitude) {
        mGpsLatitude = latitude;
        String s = String.format("%f", mGpsLatitude);
        mTextViewGpsLatitude.setText(s);
    }

    public static void setGpsLongitude(double longitude) {
        mGpsLongitude = longitude;
        String s = String.format("%f", mGpsLongitude);
        mTextViewGpsLongitude.setText(s);
    }

    public static String getGps() {
        mGpsString = "lat=" + mGpsLatitude + "/lon=" + mGpsLongitude;
        return mGpsString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        setContentView(R.layout.activity_main);

        mSmsResultReceiver = new SmsResultReceiver(new Handler());
        mSmsResultReceiver.setReceiver(this);

        mSMSSender = new SMSSender();

        mSendButton = (Button) findViewById(R.id.send);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSMSSender.sendMessage();
            }
        });

        Button exitButton = (Button) findViewById(R.id.exit);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new MyLocationListener(this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

        mPhoneNumber = (EditText)findViewById(R.id.phoneNumber);

        mCallButton = (Button)findViewById(R.id.call);

        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:14087077237"));
                startActivity(callIntent);
            }
        });

        mTextViewIncomingPhoneNumber = (TextView)findViewById(R.id.incomingPhoneNumber);
        mTextViewGpsLatitude = (TextView)findViewById(R.id.gpsLat);
        mTextViewGpsLongitude = (TextView)findViewById(R.id.gpsLon);

        mHandler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "runnable called with command = [" + mCommand + "]");
                if((mCommand != null) && mCommand.equals("CALL")) {
                    mCommand = null;
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:14087077237"));
                    startActivity(callIntent);
                }
                mHandler.postDelayed(this, 1000);
            }
        };

        mHandler.post(runnable);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    public class SmsReceiver extends BroadcastReceiver {
//
//        public SmsReceiver() {
//            super();
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            final Bundle bundle = intent.getExtras(); // Retrieves a map of extended data from the intent.
//            try {
//                if (bundle != null) {
//                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
//                    for (int i = 0; i < pdusObj.length; i++) {
//
//                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//                        String senderNum = phoneNumber;
//                        String message = currentMessage.getDisplayMessageBody();
//
//                        Log.i("XXXSmsReceiver", "senderNum: "+ senderNum + "; message: [" + message + "]");
//
//                        if(message.equals("SEND GPS")) {
//
//                            mPhoneNumber.setText(senderNum);
//
//                            Log.i(TAG, "sending sms");
//                            // String phoneNo = mPhoneNumber.getText().toString();
//                            String phoneNo = "14087077237";
//                            String omessage = "No Location Information Available";
//                            Log.i(TAG, "Sending SMS = [" + omessage + "]");
//
//                            try {
//                                SmsManager smsManager = SmsManager.getDefault();
//                                smsManager.sendTextMessage(phoneNo, null, omessage, null, null);
//                                Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            Log.i(TAG, "I did send it you know");
//                        }
//
//                        // Show Alert
//                        int duration = Toast.LENGTH_LONG;
//                        Toast toast = Toast.makeText(context,
//                                "senderNum: " + senderNum + ", message: " + message, duration);
//                        toast.show();
//
//                    } // end for loop
//                } // bundle is null
//
//            } catch (Exception e) {
//                Log.e("SmsReceiver", "Exception smsReceiver" +e);
//
//            }
//        }
//
//    }

    @Override
    public void onReceiveResult(int resultCode, Bundle bundle) {
        String message = bundle.getString("sms");
        Log.i(TAG, "you bad: message received = [" + message + "]");
    }

}
