package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by wnw on 2017/5/27.
 */

public interface IFindSumPrice {
    /**
     * 加载数据
     * */
    void findSumPrice(Context context, FindSumPriceListener findSumPriceListener);

    /**
     * 加载数据完成的回调
     * */
    interface FindSumPriceListener{
        void complete(ArrayList<Integer> days, ArrayList<Integer> months);
    }
}
