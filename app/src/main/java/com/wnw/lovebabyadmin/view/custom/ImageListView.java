package com.wnw.lovebabyadmin.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by wnw on 2017/5/24.
 */

public class ImageListView extends ListView {
    public ImageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ImageListView(Context context) {
        super(context);
    }
    public ImageListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
