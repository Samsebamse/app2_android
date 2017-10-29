package com.example.sami.s305047;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Sami on 24-Oct-17.
 */

public class MyServiceWeekly extends Service {

    public MyServiceWeekly(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msg = intent.getStringExtra("Message");

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        WakeLocker.release();
        return super.onStartCommand(intent, flags, startId);
    }
}
