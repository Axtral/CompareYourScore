package com.rougevincloud.chat.custom_views;

import android.support.v4.view.ViewPager;
import android.view.View;

public class CustomPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position <= -1)
            view.setAlpha(0);
        else if (position < 1) {
            view.setAlpha(1-Math.abs(position));
            view.setTranslationX( -pageWidth*position );
            float scale = 1 - 1f*position;
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setRotation(position*360);
        }
        else
            view.setAlpha(0);
    }
}
