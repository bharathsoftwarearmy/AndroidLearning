package com.bijesh.exchange.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bijesh.exchange.myapplication.R;

/**
 * Created by bijesh on 5/21/2016.
 */
public class HomeFragment extends BaseFragment{

    private View mRootView;
    private ListView mStockListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.home_fragment,container,false);
            initComponents(mRootView);
        }
        return mRootView;
    }

    private void initComponents(View mRootView){
        mStockListView = (ListView) mRootView.findViewById(R.id.stockList);

    }

//    private void callValidatePaymentService() {
//        setValidateRequestObject();
//        params.clear();
//        screen = ProgressDialog.show(getActivity(), "", BPMessageConstants.BP_VALIDATE_PAYMENTS_MESSAGE, true);
//        String url = securePref.getString(BPMessageConstants.BP_HOST_URLPATH) + BPMessageConstants.BP_SENDMONEY_VALIDATE_PAYMENT;
//        gsonRequest = new GsonRequest(Request.Method.POST, VolleyUtil.getUrl(params, securePref, url), BillpayValidatePaymentResponse.class, validatePaymentRequest, this, this, VolleyUtil.getHeaders(securePref));
//        requestQueue.add(gsonRequest);
//    }
}
