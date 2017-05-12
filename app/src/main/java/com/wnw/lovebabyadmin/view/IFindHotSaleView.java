package com.wnw.lovebabyadmin.view;


import com.wnw.lovebabyadmin.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindHotSaleView {
    //显示进度条
    void showLoading();
    //展示用户数据
    void showHotSale(List<Product> products);
}
