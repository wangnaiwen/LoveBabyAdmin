package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;


/**
 * Created by wnw on 2017/4/18.
 */

public interface IFindProductByIdModel {

    void findProductById(Context context, int id, ProductFindByIdListener productFindByIdListener);

    interface ProductFindByIdListener{
        void complete(Product product);
    }
}
