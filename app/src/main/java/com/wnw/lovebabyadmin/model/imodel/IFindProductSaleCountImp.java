package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.ProductSaleCount;

import java.util.List;

/**
 * Created by wnw on 2017/5/28.
 */

public interface IFindProductSaleCountImp {
    /**
     * find data
     * */
    void findProductSaleCount(Context context, FindProductSaleCountListener findProductSaleCountListener);

    /**
     * completed
     * */
    interface FindProductSaleCountListener{
        void complete(List<ProductSaleCount> productSaleCounts);
    }
}
