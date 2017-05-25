package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.ProductImage;

import java.util.List;

/**
 * Created by wnw on 2017/5/25.
 */

public interface IFindProductImagesByProductId {
    /**
     * find data
     * */
    void findImagesByProductId(Context context, int productId, FindImagesByProductIdListener findImagesByProductIdListener);

    /**
     * completed
     * */
    interface FindImagesByProductIdListener{
        void complete(List<ProductImage> images);
    }
}
