package com.example.sami.s305047;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Sami on 24-Oct-17.
 */

public class MyBroadcastReceiverWeekly extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("TAKAKU!");
        Intent i = new Intent(context, MyServiceWeekly.class);
        i.putExtra("Message", intent.getStringExtra("Message"));
        context.startService(i);
    }
}
