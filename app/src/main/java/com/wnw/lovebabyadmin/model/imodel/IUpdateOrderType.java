package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;


/**
 * Created by wnw on 2017/5/8.
 */

public interface IUpdateOrderType {
    /**
     * 加载数据
     * */
    void updateOrderType(Context context, int id, int type, OrderUpdateListener orderUpdateListener);

    /**
     * 加载数据完成的回调
     * */
    interface OrderUpdateListener{
        void complete(boolean isSuccess);
    }
}
