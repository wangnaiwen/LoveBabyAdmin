package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;


import com.wnw.lovebabyadmin.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindHotSaleModel {
    /**
     * find data
     * */
    void findHotSale(Context context, IFindHotSaleModel.FindHotSaleListener findHotSaleListener);

    /**
     * completed
     * */
    interface FindHotSaleListener{
        void complete(List<Product> productList);
    }
}
