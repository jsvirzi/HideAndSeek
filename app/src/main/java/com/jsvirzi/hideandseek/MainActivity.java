package com.jsvirzi.hideandseek;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraDevice;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private CameraDevice mCameraDevice;
    public EditText mPhoneNumber;
    public LocationManager mlocManager;
    private Button mSendButton;
    private Button mCallButton;
    public SMSReceiver mSMSReceiver;
    public SMSSender mSMSSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSMSReceiver = new SMSReceiver();
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

        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mSMSReceiver);

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

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
