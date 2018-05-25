package com.example.giovanni.agenda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Giovanni on 24/05/2018.
 */

public class AlertaNice extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       // context.startService(new Intent(context, MainService.class));
        context.startActivity(new Intent(context, MsgBroad.class));
    }
}
