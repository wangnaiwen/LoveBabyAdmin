package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IUpdateShopType {
    /**
     * 加载数据
     * */
    void updateShopType(Context context, int id, int type, ShopUpdateListener shopUpdateListener);

    /**
     * 加载数据完成的回调
     * */
    interface ShopUpdateListener{
        void complete(boolean isSuccess);
    }
}
