package com.bijesh.exchange.myapplication.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.bijesh.exchange.myapplication.R;

/**
 * Created by Bijesh on 10/31/2016.
 */

public class TriggerInputDialog extends Dialog{

    private Button mBtnOK,mBtnCancel;

    public TriggerInputDialog(Context context) {
        super(context);
    }

    public TriggerInputDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TriggerInputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
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

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TriggerInputDialog.this.dismiss();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TriggerInputDialog.this.dismiss();
            }
        });

    }
}
