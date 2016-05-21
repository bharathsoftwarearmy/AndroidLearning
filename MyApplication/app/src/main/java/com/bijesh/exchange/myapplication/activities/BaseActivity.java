package com.bijesh.exchange.myapplication.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bijesh.exchange.myapplication.fragments.BaseFragment;

/**
 * Created by Admin on 5/21/2016.
 */
public class BaseActivity extends AppCompatActivity {


    protected void fragmentTransaction(int containerId,BaseFragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId,fragment);
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

}
