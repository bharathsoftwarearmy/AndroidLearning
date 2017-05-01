package com.bijesh.exchange.myapplication.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AndroidException;
import android.widget.Toast;

import com.bijesh.exchange.myapplication.constants.ApplicationConstants;
import com.bijesh.exchange.myapplication.fragments.HomeFragment;
import com.bijesh.exchange.myapplication.logging.MyLog;
import com.bijesh.exchange.myapplication.restservices.asynctasks.DownloadStockDataTask;
import com.bijesh.exchange.myapplication.utils.DBUtils;
import com.bijesh.exchange.myapplication.utils.DateAndTime;
import com.bijesh.exchange.myapplication.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

/**
 * Created by Bijesh on 10/23/2016.
 */

public class ApplicationService extends Service implements ApplicationConstants{

    private static final String TAG = ApplicationService.class.getSimpleName();
    private Timer timer;
    private TimerTask timerTask;
    private final Handler handler = new Handler();
    private Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.w(TAG,"onCreate...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.w(TAG,"onStartCommand...");
        if(shouldDownloadStockDetails()) {
            MyLog.w(TAG,"shouldDownloadStockDetails returns true...");
            mContext = this;
            startTimer();
        }
        return START_STICKY;
    }

    private boolean shouldDownloadStockDetails(){
        MyLog.w(TAG,"shouldDownloadStockDetails...");
        boolean retFlag = false;
        if(!DateAndTime.isWeekEnd() && DateAndTime.isMarketHours()){
            return true;
        }
        return retFlag;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void startTimer() {
        MyLog.w(TAG,"timer started...");
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000,WEB_SERVICE_CALL_TIME_INTERVAL); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if(timerTask != null){
            timerTask.cancel();
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        MyLog.w(TAG,"timer thread running...");
//                        Toast toast = Toast.makeText(getApplicationContext(), "is WeekEnd "+DateAndTime.isWeekEnd()+" "
//                                +" is Market hr : "+DateAndTime.isMarketHours(), Toast.LENGTH_LONG);
//                        toast.show();
                        DownloadStockDataTask task = new DownloadStockDataTask(mContext);
                        List<String> urls = StringUtil.getShareUrls(DBUtils.getAllShare());
                        task.execute(urls);
                    }
                });
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            stoptimertask();
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent("com.bijesh.exchange.myapplication");
        sendBroadcast(intent);
    }
}
