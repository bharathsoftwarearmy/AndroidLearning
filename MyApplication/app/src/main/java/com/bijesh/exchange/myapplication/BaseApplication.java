package com.bijesh.exchange.myapplication;

import android.app.Application;

/**
 * Created by bjayaprakash on 5/23/2016.
 */
public class BaseApplication extends Application {
    protected RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = CustomRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
    }
}
