package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.SpecialPrice;

/**
 * Created by wnw on 2017/5/25.
 */

public interface IInsertSpecialPrice {
    /**
     * 加载数据
     * */
    void insertSpecialPrice(Context context, SpecialPrice specialPrice,InsertSpecialPriceListener insertSpecialPriceListener);

    /**
     * 加载数据完成的回调
     * */
    interface InsertSpecialPriceListener{
        void complete(boolean isSuccess);
    }
}
