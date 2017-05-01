package com.bijesh.exchange.myapplication.utils;

import android.util.Log;

import com.bijesh.exchange.myapplication.BaseApplication;
import com.bijesh.exchange.myapplication.constants.DBConstants;
import com.bijesh.exchange.myapplication.contentproviders.MyApplicationDBHandler;
import com.bijesh.exchange.myapplication.models.dbmodels.Share;

import java.util.List;

/**
 * Created by Bijesh on 10/28/2016.
 */

public class DBUtils implements DBConstants {

    private static final String TAG = DBUtils.class.getSimpleName();

    public static String createUserTable(){
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "("+
                COLUMN_USER_NAME + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        Log.d(TAG,CREATE_USER_TABLE);
        return CREATE_USER_TABLE;
    }

    public static String createShareDetailsTable(){
        String table = "CREATE TABLE " + TABLE_SHARE_DETAILS + "("+COLUMN_ID+" INTEGER PRIMARY KEY, "+
                COLUMN_SHARE_NAME + " TEXT, " + COLUMN_SHARE_SYMBOL + " TEXT UNIQUE, "+
        COLUMN_SHARE_PREVIOUS_NOTIFICATION_TIME+ " TEXT DEFAULT \"NA\" , "+COLUMN_SHARE_TRIGGER_PRICE
        +" TEXT DEFAULT \"0\" ,"+COLUMN_SHARE_COMMENTS+" TEXT  )";
        Log.d(TAG,table);
        return table;
    }

    public static List<Share> getAllShare(){
        MyApplicationDBHandler dbHandler = BaseApplication.getDBHandler();
        List<Share> shares = dbHandler.getAllShares();
        Log.d(TAG,"$$$ all shares "+shares);
        return shares;
    }

    public static long getPreviousNotificationTimeAsLong(String string){
        if(string.contains("NA")){
            return 0;
        }else{
            return Long.parseLong(string);
        }
    }


}
