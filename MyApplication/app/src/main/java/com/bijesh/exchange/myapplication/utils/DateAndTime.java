package com.bijesh.exchange.myapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Bijesh on 10/23/2016.
 */

public class DateAndTime {


    public static final String getCurrentTime(){
        //get the current timeStamp
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        final String strDate = simpleDateFormat.format(calendar.getTime());
        return strDate;
    }

    public static boolean isWeekEnd(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.SATURDAY:
            case Calendar.SUNDAY:
                return true;
            default:return false;
        }
    }

    public static boolean isMarketHours(){
        Calendar calendar = Calendar.getInstance();
        int marketOpenHour = 9,marketCloseHour=16;
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        if((currentHour >= marketOpenHour) && (currentHour < marketCloseHour)){
            return true;
        }else {
            return false;
        }
    }


}
