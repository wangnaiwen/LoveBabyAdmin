package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.ProductImage;


/**
 * Created by wnw on 2017/5/25.
 */

public interface IInsertProductImage {
    /**
     * find data
     * */
    void insertProductImage(Context context, ProductImage image, InsertProductImageListener insertProductImageListener);

    /**
     * completed
     * */
    interface InsertProductImageListener{
        void complete(boolean isSuccess);
    }
}
