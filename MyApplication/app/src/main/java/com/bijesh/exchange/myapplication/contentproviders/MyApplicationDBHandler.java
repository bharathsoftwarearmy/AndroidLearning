package com.bijesh.exchange.myapplication.contentproviders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bijesh.exchange.myapplication.constants.DBConstants;
import com.bijesh.exchange.myapplication.models.dbmodels.Share;
import com.bijesh.exchange.myapplication.models.dbmodels.User;
import com.bijesh.exchange.myapplication.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bijesh on 10/2/2016.
 */

public class MyApplicationDBHandler extends SQLiteOpenHelper implements DBConstants{

    private static final String TAG = MyApplicationDBHandler.class.getCanonicalName();

    public MyApplicationDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = DBUtils.createUserTable();
        String CREATE_SHARE_TABLE = DBUtils.createShareDetailsTable();
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SHARE_TABLE);
        Log.d(TAG,"Created tables "+TABLE_USERS +", "+TABLE_SHARE_DETAILS+" successfully...");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHARE_DETAILS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new user
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }


    // Adding new Share
    public void addShare(Share share) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, share.getShareName());

        // Inserting Row
        db.insert(TABLE_SHARE_DETAILS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one User
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID,
                        COLUMN_USER_NAME, COLUMN_PASSWORD}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User();
        user.setUserName(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        return user;
    }

    // Getting one Share
    public Share getShare(String symbol) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SHARE_DETAILS, new String[]{COLUMN_ID,
                        COLUMN_SHARE_NAME, COLUMN_SHARE_SYMBOL,COLUMN_SHARE_PREVIOUS_NOTIFICATION_TIME
        ,COLUMN_SHARE_TRIGGER_PRICE}, COLUMN_SHARE_SYMBOL + "=?",
                new String[]{symbol}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Share share = new Share();
        share.setShareName(cursor.getString(1));
        share.setShareSymbol(cursor.getString(2));
        share.setPreviousNotificationTime(DBUtils.getPreviousNotificationTimeAsLong(cursor.getString(3)));
        share.setTriggerPrice(Double.parseDouble(cursor.getString(4)));
        return share;
    }

    /**
     *
     * @param share
     * @return
     */
    public boolean insertShare  (Share share){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SHARE_NAME, share.getShareName());
        contentValues.put(COLUMN_SHARE_SYMBOL, share.getShareSymbol());
        db.insert(TABLE_SHARE_DETAILS, null, contentValues);
        Log.d(TAG,"Inserted "+share.getShareSymbol()+" successfully...");
        return true;
    }


//     Getting All Shares
    public List<Share> getAllShares() {
        List<Share> shareList = new ArrayList<Share>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SHARE_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Share share = new Share();
//                shop.setId(Integer.parseInt(cursor.getString(0)));
                share.setShareName(cursor.getString(1));
                share.setShareSymbol(cursor.getString(2));
                share.setPreviousNotificationTime(DBUtils.getPreviousNotificationTimeAsLong(cursor.getString(3)));
                // Adding share to list
                shareList.add(share);
            } while (cursor.moveToNext());
        }

        // return share list
        return shareList;
    }

//     Updating a Share
    public int updateShare(Share share) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SHARE_PREVIOUS_NOTIFICATION_TIME, share.getPreviousNotificationTime());
        values.put(COLUMN_SHARE_TRIGGER_PRICE,share.getTriggerPrice());
        // updating row
        return db.update(TABLE_SHARE_DETAILS, values, COLUMN_SHARE_SYMBOL + " = ?",
                new String[]{share.getShareSymbol()});
    }


        // Deleting a share
    public void deleteShare(Share share) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHARE_DETAILS, COLUMN_SHARE_SYMBOL + " = ?",
                new String[] { share.getShareSymbol() });
        db.close();
    }

//    // Getting shops Count
//    public int getShopsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_SHOPS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }

    // Updating a User
//    public int updateUser(User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_NAME, user.getUserName());
//        values.put(COLUMN_PASSWORD, user.getPassword());
//
//        // updating row
//        return db.update(TABLE_USERS, values, COLUMN_ID + " = ?",
//                new String[]{String.valueOf(shop.getId())});
//    }
//



}
