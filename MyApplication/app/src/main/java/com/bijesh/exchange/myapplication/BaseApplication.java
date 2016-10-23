package com.bijesh.exchange.myapplication;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.bijesh.exchange.myapplication.restservices.CustomRequestQueue;

/**
 * Created by bjayaprakash on 5/23/2016.
 */
public class BaseApplication extends Application {
    private RequestQueue requestQueue;
    private static BaseApplication mBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        if(requestQueue == null) {
            requestQueue = CustomRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        }
        mBaseApplication = this;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = CustomRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        }
        return requestQueue;
    }

    public static BaseApplication getBaseApplication(){
        return mBaseApplication;
    }

}
