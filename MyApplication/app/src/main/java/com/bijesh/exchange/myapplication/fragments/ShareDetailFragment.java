package com.bijesh.exchange.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bijesh.exchange.myapplication.BaseApplication;
import com.bijesh.exchange.myapplication.R;
import com.bijesh.exchange.myapplication.constants.ApplicationConstants;
import com.bijesh.exchange.myapplication.contentproviders.MyApplicationDBHandler;
import com.bijesh.exchange.myapplication.dialogs.TriggerInputDialog;
import com.bijesh.exchange.myapplication.models.dbmodels.Share;
import com.bijesh.exchange.myapplication.models.webservicemodels.Stock;

/**
 * Created by Bijesh on 10/29/2016.
 */

public class ShareDetailFragment extends BaseFragment implements ApplicationConstants{

    private View mRootView;
    private Stock mStock;
    private TextView mTxtViewShareName,mTxtViewTriggerPrice;
    private FloatingActionButton mFabNotify;
    private MyApplicationDBHandler myApplicationDBHandler;
    private Share mShare;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.share_detail_fragment,container,false);
            Bundle bundle = getArguments();
            if(bundle != null){
                mStock = (Stock)bundle.get(BUNDLE_STOCK);
            }
            initComponents(mRootView,mStock);
        }
        return mRootView;
    }

    private void initComponents(View rootView, Stock stock){
        if(stock != null){
            myApplicationDBHandler = BaseApplication.getDBHandler();
            mShare = myApplicationDBHandler.getShare(stock.getSymbol());

            mTxtViewShareName = (TextView)rootView.findViewById(R.id.txtViewShareName);
            mTxtViewTriggerPrice = (TextView) rootView.findViewById(R.id.txtViewTriggerPrice);
            mFabNotify = (FloatingActionButton) rootView.findViewById(R.id.fabNotify);

            mTxtViewShareName.setText(stock.getCompanyName());
            mTxtViewTriggerPrice.setText(mShare.getTriggerPrice()+"");


            mFabNotify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TriggerInputDialog triggerInputDialog = new TriggerInputDialog(getActivity().getBaseContext());
                    triggerInputDialog.show();
                }
            });

        }
    }

}
