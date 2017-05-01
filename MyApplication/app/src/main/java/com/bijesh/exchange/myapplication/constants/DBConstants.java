package com.bijesh.exchange.myapplication.constants;

/**
 * Created by Bijesh on 10/28/2016.
 */

public interface DBConstants {

    // Database Version
    public static final int DATABASE_VERSION = 2;

    // Database Name
    public static final String DATABASE_NAME = "MyApplication";

    // Contacts table name
    public static final String TABLE_USERS = "users";
    public static final String TABLE_SHARE_DETAILS = "share_details";

    // User Table Columns names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_PASSWORD = "password";


//    Share detials Table Columns name
    String COLUMN_SHARE_NAME = "ShareName";
    String COLUMN_SHARE_SYMBOL = "ShareSymbol";
    String COLUMN_SHARE_PREVIOUS_NOTIFICATION_TIME = "PreviousNofiticationTime";
    String COLUMN_SHARE_TRIGGER_PRICE = "TriggerPrice";
    String COLUMN_SHARE_COMMENTS = "Comments";


}
