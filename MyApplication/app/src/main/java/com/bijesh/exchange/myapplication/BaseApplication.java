package com.bijesh.exchange.myapplication;

import android.app.Application;
import android.content.Intent;
import android.content.res.AssetManager;

import com.android.volley.RequestQueue;
import com.bijesh.exchange.myapplication.contentproviders.MyApplicationDBHandler;
import com.bijesh.exchange.myapplication.logging.MyLog;
import com.bijesh.exchange.myapplication.models.parsers.AllShareSymbols;
import com.bijesh.exchange.myapplication.models.parsers.ShareSymbol;
import com.bijesh.exchange.myapplication.restservices.CustomRequestQueue;
import com.bijesh.exchange.myapplication.services.ApplicationService;
import com.bijesh.exchange.myapplication.utils.JsonPojoConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by bjayaprakash on 5/23/2016.
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();
    private RequestQueue requestQueue;
    private static BaseApplication mBaseApplication;
    private static MyApplicationDBHandler mDBHandler;
    private ShareSymbol shareSymbol = null;
    private AllShareSymbols mAllShareSymbols;
    private List<ShareSymbol> allShareSymbols;
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
//        initialize the share symbols
        initShareSymbols();
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

    private List<ShareSymbol> initShareSymbols(){
        AssetManager assetManager = getAssets();
        // To load text file
        InputStream input;
        try {
            input = assetManager.open("nse_symbols.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            // byte buffer into a string
            String text = new String(buffer);
//            allShareSymbols = (List<ShareSymbol>) JsonPojoConverter.convertJsonToPojo(text,ShareSymbol.class);
            Type collectionType = new TypeToken<List<ShareSymbol>>(){}.getType();
           allShareSymbols = new Gson()
                    .fromJson( text , collectionType);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allShareSymbols;
    }

    public List<ShareSymbol> getShareSymbol(){
        if(allShareSymbols != null){
            return allShareSymbols;
        }else{
            this.allShareSymbols = initShareSymbols();
        }
        return allShareSymbols;
    }



}
