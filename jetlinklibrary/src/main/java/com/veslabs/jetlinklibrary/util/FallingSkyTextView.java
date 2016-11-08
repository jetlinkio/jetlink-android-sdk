package com.veslabs.jetlinklibrary.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Burhan Aras on 11/8/2016.
 */
public class FallingSkyTextView extends TextView {
    private static final String TAG = FallingSkyTextView.class.getSimpleName();

    public FallingSkyTextView(Context context) {
        super(context);
        setFont();
    }

    public FallingSkyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public FallingSkyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }
    public void setFont(){
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "FallingSky.otf");
        setTypeface(custom_font);
    }
}
