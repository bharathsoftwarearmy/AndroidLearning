package com.bijesh.exchange.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bijesh.exchange.myapplication.BaseApplication;
import com.bijesh.exchange.myapplication.R;
import com.bijesh.exchange.myapplication.constants.ApplicationConstants;
import com.bijesh.exchange.myapplication.contentproviders.MyApplicationDBHandler;
import com.bijesh.exchange.myapplication.customcomponents.uicomponents.MyApplicationButton;
import com.bijesh.exchange.myapplication.dialogs.TriggerInputDialog;
import com.bijesh.exchange.myapplication.models.dbmodels.Share;
import com.bijesh.exchange.myapplication.models.webservicemodels.Stock;
import com.bijesh.exchange.myapplication.utils.FragmentUtil;

/**
 * Created by Bijesh on 10/29/2016.
 */

public class ShareDetailFragment extends BaseFragment implements ApplicationConstants,TriggerInputDialog.OnInputDialogReceivedListener {

    private static final String TAG = ShareDetailFragment.class.getSimpleName();
    private View mRootView;
    private Stock mStock;
    private TextView mTxtViewShareName,mTxtViewTriggerPrice;
    private FloatingActionButton mFabNotify,mFabDelete;
    private MyApplicationDBHandler myApplicationDBHandler;
    private MyApplicationButton mBtnDelete;
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

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initComponents(View rootView, Stock stock){
        if(stock != null){
            myApplicationDBHandler = BaseApplication.getDBHandler();
            mShare = myApplicationDBHandler.getShare(stock.getSymbol());

            mTxtViewShareName = (TextView)rootView.findViewById(R.id.txtViewShareName);
            mTxtViewTriggerPrice = (TextView) rootView.findViewById(R.id.txtViewTriggerPrice);
            mFabNotify = (FloatingActionButton) rootView.findViewById(R.id.fabNotify);
            mFabDelete = (FloatingActionButton) rootView.findViewById(R.id.fabDelete);

            mTxtViewShareName.setText(stock.getCompanyName());
            mTxtViewTriggerPrice.setText(mShare.getTriggerPrice()+"");

            mBtnDelete = (MyApplicationButton) rootView.findViewById(R.id.btnDelete);


            mBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplicationDBHandler dbHandler = BaseApplication.getDBHandler();
                    dbHandler.deleteShare(mShare);
                    FragmentUtil.removeFragment(getActivity(),ShareDetailFragment.this);
                }
            });


            mFabNotify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(BUNDLE_AMOUNT,mTxtViewTriggerPrice.getText().toString());
                    TriggerInputDialog triggerInputDialog = new TriggerInputDialog(getActivity(),ShareDetailFragment.this);
                    triggerInputDialog.setArguments(bundle);
                    triggerInputDialog.show();
                }
            });

            mFabDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplicationDBHandler dbHandler = BaseApplication.getDBHandler();
                    dbHandler.deleteShare(mShare);
                    FragmentUtil.removeFragment(getActivity(),ShareDetailFragment.this);
                }
            });

        }
    }

    @Override
    public void onInputReceived(Bundle bundle) {
        if(bundle != null){
            double price = bundle.getDouble(BUNDLE_TRIGGER_PRICE);
            mTxtViewTriggerPrice.setText(""+price);
            MyApplicationDBHandler dbHandler = BaseApplication.getDBHandler();
            mShare.setTriggerPrice(price);
            dbHandler.updateShare(mShare);
        }
    }
}
