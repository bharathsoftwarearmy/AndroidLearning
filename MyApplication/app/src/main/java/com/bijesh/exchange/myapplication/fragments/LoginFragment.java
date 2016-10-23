package com.bijesh.exchange.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bijesh.exchange.myapplication.R;

/**
 * Created by Bijesh on 9/24/2016.
 */

public class LoginFragment extends BaseFragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.login_fragment,container,false);
            initComponents(mRootView);
        }
        return mRootView;
    }

    private void initComponents(View rootView){

    }


}
