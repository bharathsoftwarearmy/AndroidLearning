package com.bijesh.exchange.myapplication;

import android.app.Application;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.bijesh.exchange.myapplication.contentproviders.MyApplicationDBHandler;
import com.bijesh.exchange.myapplication.logging.MyLog;
import com.bijesh.exchange.myapplication.restservices.CustomRequestQueue;
import com.bijesh.exchange.myapplication.services.ApplicationService;

/**
 * Created by bjayaprakash on 5/23/2016.
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();
    private RequestQueue requestQueue;
    private static BaseApplication mBaseApplication;
    private static MyApplicationDBHandler mDBHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.w(TAG,"onCreate...");
        if(requestQueue == null) {
            requestQueue = CustomRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        }
        mBaseApplication = this;

//        initialize logger file


        Intent serviceIntent = new Intent(this, ApplicationService.class);
        startService(serviceIntent);
        // initiliaze db
        mDBHandler = new MyApplicationDBHandler(this);
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

    public static MyApplicationDBHandler getDBHandler(){
        if(mDBHandler == null){
            mDBHandler = new MyApplicationDBHandler(mBaseApplication);
        }
        return mDBHandler;
    }

}
