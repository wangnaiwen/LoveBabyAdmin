package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Shop;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IFindShopByType {
    /**
     * 加载数据
     * */
    void findShop(Context context, int type, int page, ShopFindByTypeListener shopFindByTypeListener);

    /**
     * 加载数据完成的回调
     * */
    interface ShopFindByTypeListener{
        void complete(List<Shop> shops);
    }
}
