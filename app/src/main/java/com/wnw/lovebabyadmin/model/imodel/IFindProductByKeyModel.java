package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;

import java.util.List;


/**
 * Created by wnw on 2017/4/26.
 */

public interface IFindProductByKeyModel {
    /**
     * find data
     * */
    void findProductByKey(Context context, String key, int userId, FindProductByKeyListener findProductByKeyListener);

    /**
     * completed
     * */
    interface FindProductByKeyListener{
        void complete(List<Product> products);
    }
}
