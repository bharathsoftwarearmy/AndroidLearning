package com.bijesh.exchange.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.bijesh.exchange.myapplication.BaseApplication;
import com.bijesh.exchange.myapplication.restservices.CustomRequestQueue;
import com.bijesh.exchange.myapplication.restservices.GsonRequest;

/**
 * Created by Admin on 5/21/2016.
 */
public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getCanonicalName();
    protected GsonRequest gsonRequest;
    protected RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(requestQueue == null){
            requestQueue = getRequestQueue();
            Log.d(TAG,"Request Queue initialized...");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected RequestQueue getRequestQueue(){
        return BaseApplication.getBaseApplication().getRequestQueue();
    }

    protected void fragmentTransaction(int containerId, BaseFragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId,fragment);
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

}
