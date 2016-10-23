package com.bijesh.exchange.myapplication.restservices;

/**
 * Created by Bijesh on 9/16/2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.bijesh.exchange.myapplication.restservices.utils.SSLUtils;


/**
 * Custom implementation of Volley Request Queue
 */
public class CustomRequestQueue {
    private static final long serialVersionUID = 8161610343375187070L;


    private static CustomRequestQueue mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
//    private SecureSharedPreferences securePref;

    private CustomRequestQueue(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache(){
                    private final LruCache<String,Bitmap> cache = new LruCache<>(20);
                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url,bitmap);
                    }
                });
    }

    public static synchronized CustomRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CustomRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);
//            securePref =SecureSharedPreferences.getInstance(mCtx, BPMessageConstants.BP_SECURE_PREF_KEY, BPMessageConstants.BP_SECURE_PREF_KEY_VALUE, true);
//            String envValue = securePref.getString(BPMessageConstants.BP_ENV_VALUE);
          //  BPCustomLog.d("CustomRequestQueue:: env",envValue);
            HurlStack hurlStack=null;
//            if(!BPMessageConstants.BP_PROD_ENV.equalsIgnoreCase(envValue)){
                hurlStack = new HurlStack(null, SSLUtils.createSslSocketFactory());
//            }else{
//                hurlStack = new HurlStack();
//            }
            Network network = new BasicNetwork(hurlStack);
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public ImageLoader getmImageLoader(){
        return mImageLoader;
    }

}
