package com.bijesh.exchange.myapplication.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bijesh.exchange.myapplication.services.ApplicationService;

/**
 * Created by Bijesh on 5/1/2017.
 */

public class RelaunchApplicationServiceReciever  extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ApplicationService.class));
    }


}
