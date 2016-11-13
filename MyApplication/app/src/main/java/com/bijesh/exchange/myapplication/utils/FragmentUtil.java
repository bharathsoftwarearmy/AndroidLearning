package com.bijesh.exchange.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.bijesh.exchange.myapplication.fragments.BaseFragment;

/**
 * Created by Bijesh on 11/13/2016.
 */

public class FragmentUtil {

    public static void removeFragment(FragmentActivity activity, BaseFragment fragment){
        activity.getSupportFragmentManager().popBackStack();
    }

}
