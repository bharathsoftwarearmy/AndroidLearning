package com.bijesh.exchange.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bijesh.exchange.myapplication.fragments.BaseFragment;
import com.bijesh.exchange.myapplication.fragments.HomeFragment;

import java.util.List;

/**
 * Created by Admin on 5/21/2016.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void fragmentTransaction(int containerId, BaseFragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId,fragment);
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackHandle();
    }

    private void onBackHandle(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        exitApplication(fragmentManager.getBackStackEntryCount());
    }

    private void exitApplication(int count){
        if(count == 0)
           finish();
    }

}
