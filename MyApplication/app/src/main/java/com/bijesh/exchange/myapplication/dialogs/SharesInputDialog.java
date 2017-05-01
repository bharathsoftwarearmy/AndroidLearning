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

public class SharesInputDialog extends Dialog implements ApplicationConstants{

    private Button mBtnOK,mBtnCancel;
    private EditText mEdtTxtTriggerPrice;
    private OnShareDialogReceivedListener mFragment;
    private Bundle mBundle;

    public SharesInputDialog(Context context, OnShareDialogReceivedListener baseFragment) {
        super(context);
        this.mFragment = baseFragment;
    }

    public SharesInputDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SharesInputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
        setContentView(R.layout.dialog_shares);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        mBtnOK = (Button)findViewById(R.id.btnOk);
        mBtnCancel = (Button)findViewById(R.id.btnCancel);
        mEdtTxtTriggerPrice = (EditText)findViewById(R.id.edtTxtPrice);


        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharesInputDialog.this.dismiss();
                Bundle bundle = new Bundle();
                if(StringUtil.isNotNullOrEmpty(mEdtTxtTriggerPrice.getText().toString().trim())) {
                    bundle.putInt(BUNDLE_SHARES_NUMBER, Integer.parseInt(mEdtTxtTriggerPrice.getText().toString().trim()));
                }
                mFragment.onSharesReceived(bundle);
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharesInputDialog.this.dismiss();
            }
        });

    }


    public interface OnShareDialogReceivedListener{
        public void onSharesReceived(Bundle bundle);
    }

}
