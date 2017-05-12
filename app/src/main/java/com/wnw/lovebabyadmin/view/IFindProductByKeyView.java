package com.wnw.lovebabyadmin.view;


import com.wnw.lovebabyadmin.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/26.
 */

public interface IFindProductByKeyView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showProductByUserId(List<Product> products);
}
