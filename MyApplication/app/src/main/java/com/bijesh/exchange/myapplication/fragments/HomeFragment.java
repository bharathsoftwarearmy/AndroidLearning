package com.bijesh.exchange.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bijesh.exchange.myapplication.R;

/**
 * Created by bijesh on 5/21/2016.
 */
public class HomeFragment extends Fragment{

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.home_fragment,null,true);
        }
        return mRootView;
    }


}
