package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/5/12.
 */

public interface IFindProductByKeyword {
    /**
     * 加载数据
     * */
    void findProductByKeyword(Context context, String keyword, ProductFindByKeywordListener productFindByKeywordListener);

    /**
     * 加载数据完成的回调
     * */
    interface ProductFindByKeywordListener{
        void complete(List<Product> products);
    }
}
