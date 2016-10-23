package com.bijesh.exchange.myapplication.contentproviders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bijesh.exchange.myapplication.models.dbmodels.User;

/**
 * Created by Bijesh on 10/2/2016.
 */

public class MyApplicationDBHandler extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ShareInfo";

    // Contacts table name
    private static final String TABLE_USERS = "users";

    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_PASSWORD = "password";

    public MyApplicationDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERS + "("+
                 KEY_USER_NAME + " TEXT PRIMARY KEY,"
                + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new user
    public void addShop(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUserName());
        values.put(KEY_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one User
    public User getShop(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_USER_NAME, KEY_PASSWORD}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User();
        user.setUserName(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        return user;
    }

    // Getting All Users
//    public List<Shop> getAllShops() {
//        List<Shop> shopList = new ArrayList<Shop>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_SHOPS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Shop shop = new Shop();
//                shop.setId(Integer.parseInt(cursor.getString(0)));
//                shop.setName(cursor.getString(1));
//                shop.setAddress(cursor.getString(2));
//                // Adding contact to list
//                shopList.add(shop);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return shopList;
//    }

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
//        values.put(KEY_USER_NAME, user.getUserName());
//        values.put(KEY_PASSWORD, user.getPassword());
//
//        // updating row
//        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(shop.getId())});
//    }
//
//    // Deleting a shop
//    public void deleteShop(Shop shop) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SHOPS, KEY_ID + " = ?",
//                new String[] { String.valueOf(shop.getId()) });
//        db.close();
//    }


}
