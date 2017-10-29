package com.example.sami.s305047;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sami on 24-Oct-17.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mainActivity = new Intent(context, MainActivity.class);
        context.startActivity(mainActivity);

        Intent i = new Intent(context, MyService.class);
        i.putExtra("Message", intent.getStringExtra("Message"));
        context.startService(i);
    }
}
