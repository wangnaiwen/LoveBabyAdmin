package com.wnw.lovebabyadmin.view;


import com.wnw.lovebabyadmin.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindSpecialPriceView {
    //显示进度条
    void showLoading();
    //展示用户数据
    void showSpecialPrice(List<Product> products);
}
