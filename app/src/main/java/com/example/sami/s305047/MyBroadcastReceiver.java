package com.example.sami.s305047;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Sami on 24-Oct-17.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "SERVICE IS ON FIREEEEEEEEEEEEEEEEEEE", Toast.LENGTH_SHORT).show();
    }
}
