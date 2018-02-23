package com.iprismtech.rawvana;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iprismtech.rawvana.others.Values;

public class ActivitySplash extends FragmentActivity {
    private Activity actSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Values.RECIPEID="1";
        Values.RECIPENAME="Breakfast";
        actSplash = ActivitySplash.this;
        try {
            Handler splash_time_handler = new Handler(Looper.getMainLooper());
            splash_time_handler.postDelayed(new Runnable() {
                public void run() {
                    start();;
                }//run()
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void checkPermission() {
        try {
            if (ActivityCompat.checkSelfPermission(actSplash, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(actSplash, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(actSplash, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&  ActivityCompat.checkSelfPermission(actSplash, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(actSplash, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(actSplash, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(actSplash, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.CAMERA,android.Manifest.permission.READ_PHONE_STATE}, 10);
            } else {
                start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() {
        Intent intent =new Intent(getApplicationContext(),ActivityMain.class);
        intent.putExtra("Conditions","splash");
        startActivity(intent);
    }
}
