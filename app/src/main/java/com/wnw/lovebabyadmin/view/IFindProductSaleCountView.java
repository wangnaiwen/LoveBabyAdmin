package com.wnw.lovebabyadmin.view;

import com.wnw.lovebabyadmin.domain.ProductSaleCount;

import java.util.List;

/**
 * Created by wnw on 2017/5/28.
 */

public interface IFindProductSaleCountView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showProductSaleCount(List<ProductSaleCount> saleCounts);
}
