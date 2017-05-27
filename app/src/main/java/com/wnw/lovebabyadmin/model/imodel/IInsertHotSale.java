package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.HotSale;

/**
 * Created by wnw on 2017/5/25.
 */

public interface IInsertHotSale {
    /**
     * 加载数据
     * */
    void insertHotSale(Context context, HotSale hotSale, InsertHotSaleListener insertHotSaleListener);

    /**
     * 加载数据完成的回调
     * */
    interface InsertHotSaleListener{
        void complete(boolean isSuccess);
    }
}
