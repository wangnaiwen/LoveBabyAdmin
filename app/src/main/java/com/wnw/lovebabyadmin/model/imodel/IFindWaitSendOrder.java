package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Order;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IFindWaitSendOrder {
    /**
     * 加载数据
     * */
    void findWaitOrder(Context context, int type, int page, WaitOrderFindListener waitOrderFindListener);

    /**
     * 加载数据完成的回调
     * */
    interface WaitOrderFindListener{
        void complete(List<Order> orders);
    }
}
