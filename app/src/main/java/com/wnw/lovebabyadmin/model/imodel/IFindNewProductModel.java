package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindNewProductModel {
    /**
     * find data
     * */
    void findNewProduct(Context context, IFindNewProductModel.FindNewProductListener findNewProductListener);

    /**
     * completed
     * */
    interface FindNewProductListener{
        void complete(List<Product> productList);
    }
}
