package com.bijesh.exchange.myapplication.utils;

import java.util.Random;

/**
 * Created by Bijesh on 10/28/2016.
 */

public class NumberUtil {


    public static int generateRandomNumber(){
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        return m;
    }

}
