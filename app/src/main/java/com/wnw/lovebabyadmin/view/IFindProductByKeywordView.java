package com.wnw.lovebabyadmin.view;

import com.wnw.lovebabyadmin.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/5/12.
 */

public interface IFindProductByKeywordView {
    void showDialog();
    void showProducts(List<Product> products);
}
