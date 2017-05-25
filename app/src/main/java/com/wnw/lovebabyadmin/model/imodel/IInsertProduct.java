package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;

/**
 * Created by wnw on 2017/5/12.
 */

public interface IInsertProduct {
    /**
     * 加载数据
     * */
    void insertProduct(Context context, Product product, ProductInsertListener productInsertListener );

    /**
     * 加载数据完成的回调
     * */
    interface ProductInsertListener{
        void complete(int id);
    }
}
