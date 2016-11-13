package com.bijesh.exchange.myapplication.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.bijesh.exchange.myapplication.R;
import com.bijesh.exchange.myapplication.constants.ApplicationConstants;
import com.bijesh.exchange.myapplication.fragments.BaseFragment;
import com.bijesh.exchange.myapplication.utils.StringUtil;

/**
 * Created by Bijesh on 10/31/2016.
 */

public class TriggerInputDialog extends Dialog implements ApplicationConstants{

    private Button mBtnOK,mBtnCancel;
    private EditText mEdtTxtTriggerPrice;
    private OnInputDialogReceivedListener mFragment;
    private Bundle mBundle;

    public TriggerInputDialog(Context context,BaseFragment baseFragment) {
        super(context);
        this.mFragment = baseFragment;
    }

    public TriggerInputDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TriggerInputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setArguments(Bundle bundle){
        this.mBundle = bundle;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle(R.string.dialog_trigger_price);
        setContentView(R.layout.dialog_trigger);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        mBtnOK = (Button)findViewById(R.id.btnOk);
        mBtnCancel = (Button)findViewById(R.id.btnCancel);
        mEdtTxtTriggerPrice = (EditText)findViewById(R.id.edtTxtPrice);

        if(mBundle != null){
            mEdtTxtTriggerPrice.setText(mBundle.getString(BUNDLE_AMOUNT));
        }

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TriggerInputDialog.this.dismiss();
                Bundle bundle = new Bundle();
                if(StringUtil.isNotNullOrEmpty(mEdtTxtTriggerPrice.getText().toString().trim())) {
                    bundle.putDouble(BUNDLE_TRIGGER_PRICE, Double.parseDouble(mEdtTxtTriggerPrice.getText().toString().trim()));
                }
                mFragment.onInputReceived(bundle);
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TriggerInputDialog.this.dismiss();
            }
        });

    }


    public interface OnInputDialogReceivedListener{
        public void onInputReceived(Bundle bundle);
    }

}
