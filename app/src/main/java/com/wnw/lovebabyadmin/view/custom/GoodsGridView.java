package com.wnw.lovebabyadmin.view.custom;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by wnw on 2016/12/05.
 */
public class GoodsGridView extends GridView {
    public GoodsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public GoodsGridView(Context context) {
        super(context);
    }
    public GoodsGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}