package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;

/**
 * Created by wnw on 2017/5/12.
 */

public interface IUpdateProduct {

    /**
     * 加载数据
    * */
    void updateProduct(Context context, Product product, ProductUpdateListener productUpdateListener);

    /**
     * 加载数据完成的回调
     * */
    interface ProductUpdateListener{
        void complete(boolean isSuccess);
    }
}
