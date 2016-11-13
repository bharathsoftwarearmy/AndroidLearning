package com.bijesh.exchange.myapplication.customcomponents.uicomponents;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Bijesh on 11/13/2016.
 */

public class MyApplicationButton extends Button {

    public MyApplicationButton(Context context) {
        super(context);
        setBackgroundGradient();
    }

    public MyApplicationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundGradient();
    }

    public MyApplicationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundGradient();
    }

    private void setBackgroundGradient(){
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFD10606,0xFFFE0404});
        gd.setCornerRadius(0f);
        this.setBackgroundDrawable(gd);
    }

//    public MyApplicationButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
}
