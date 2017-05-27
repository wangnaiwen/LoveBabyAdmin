package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

/**
 * Created by wnw on 2017/5/25.
 */

public interface IDeleteProduct {
    /**
     * 加载数据
     * */
    void deleteProduct(Context context, int id, ProductDeleteListener productDeleteListener);

    /**
     * 加载数据完成的回调
     * */
    interface ProductDeleteListener{
        void complete(boolean isSuccess);
    }
}
