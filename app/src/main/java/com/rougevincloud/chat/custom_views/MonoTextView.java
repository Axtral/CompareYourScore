package com.rougevincloud.chat.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MonoTextView extends TextView {
    public MonoTextView(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/cour.ttf"));
    }

    public MonoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/cour.ttf"));
    }

    public MonoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/cour.ttf"));
    }

    public MonoTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/cour.ttf"));
    }
}
