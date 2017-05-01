package com.bijesh.exchange.myapplication.fragments;

import android.os.Bundle;
import android.renderscript.Double2;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bijesh.exchange.myapplication.BaseApplication;
import com.bijesh.exchange.myapplication.R;
import com.bijesh.exchange.myapplication.constants.ApplicationConstants;
import com.bijesh.exchange.myapplication.contentproviders.MyApplicationDBHandler;
import com.bijesh.exchange.myapplication.customcomponents.uicomponents.MyApplicationButton;
import com.bijesh.exchange.myapplication.dialogs.SharesInputDialog;
import com.bijesh.exchange.myapplication.dialogs.TriggerInputDialog;
import com.bijesh.exchange.myapplication.models.dbmodels.Share;
import com.bijesh.exchange.myapplication.models.webservicemodels.Stock;
import com.bijesh.exchange.myapplication.utils.FragmentUtil;

import java.text.DecimalFormat;

/**
 * Created by Bijesh on 10/29/2016.
 */

public class ShareDetailFragment extends BaseFragment implements ApplicationConstants,TriggerInputDialog.OnInputDialogReceivedListener ,
        SharesInputDialog.OnShareDialogReceivedListener{

    private static final String TAG = ShareDetailFragment.class.getSimpleName();
    private View mRootView;
    private Stock mStock;
    private TextView mTxtViewShareName,mTxtViewTriggerPrice,mTxtViewNumberOfShares,mTxtViewCurrentValue;
    private FloatingActionButton mFabNotify,mFabDelete,mFabShares;
    private MyApplicationDBHandler myApplicationDBHandler;
    private MyApplicationButton mBtnDelete;
    private Share mShare;
    private EditText mEdtTxtComments;


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
            mTxtViewNumberOfShares = (TextView) rootView.findViewById(R.id.txtViewNumberOfShares);
            mTxtViewCurrentValue = (TextView) rootView.findViewById(R.id.txtViewCurrentValue);

            mFabNotify = (FloatingActionButton) rootView.findViewById(R.id.fabNotify);
            mFabDelete = (FloatingActionButton) rootView.findViewById(R.id.fabDelete);
            mFabShares = (FloatingActionButton) rootView.findViewById(R.id.fabShares);
            mEdtTxtComments = (EditText) rootView.findViewById(R.id.edtTxtComments);

            mTxtViewShareName.setText(stock.getCompanyName());
            mTxtViewTriggerPrice.setText(mShare.getTriggerPrice()+"");
            mTxtViewNumberOfShares.setText(mShare.getNumberOfShares()+"");
            mTxtViewCurrentValue.setText(getCurrentValue(stock.getLastPrice(),mShare.getNumberOfShares()));

            mEdtTxtComments.setText(mShare.getComments());

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

            mFabShares.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharesInputDialog sharesInputDialog = new SharesInputDialog(getActivity(),ShareDetailFragment.this);
                    sharesInputDialog.show();
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

            mEdtTxtComments.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mShare.setComments(s.toString());
                    myApplicationDBHandler.updateShareComments(mShare);
                }
            });

        }
    }

    private String getCurrentValue(String priceString,int numOfShares){
        double price = Double.parseDouble(priceString);
        if(numOfShares > 0) {
            DecimalFormat df = new DecimalFormat("####0.00");
            double val = price * numOfShares;
            return df.format(val);
        }
        return "0.00";
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

    @Override
    public void onSharesReceived(Bundle bundle) {
        if(bundle != null){
            int numberOfShares = bundle.getInt(BUNDLE_SHARES_NUMBER);
            mTxtViewNumberOfShares.setText(numberOfShares+"");
            mTxtViewCurrentValue.setText(getCurrentValue(mStock.getLastPrice(),numberOfShares));
            mShare.setNumberOfShares(numberOfShares);
            myApplicationDBHandler.updateShareNumbers(mShare);
        }
    }
}
