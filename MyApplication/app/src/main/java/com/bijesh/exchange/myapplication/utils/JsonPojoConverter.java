package com.bijesh.exchange.myapplication.utils;

import android.util.Log;

import com.bijesh.exchange.myapplication.models.webservicemodels.StockData;
import com.google.gson.Gson;

/**
 * Created by Bijesh on 10/23/2016.
 */

public class JsonPojoConverter {

    public static Object convertJsonToPojo(String responseJson,Class clazz){
        Gson gson = new Gson();
        return gson.fromJson(responseJson,clazz);
    }

}
