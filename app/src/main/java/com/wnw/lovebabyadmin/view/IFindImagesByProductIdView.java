package com.wnw.lovebabyadmin.view;

import com.wnw.lovebabyadmin.domain.ProductImage;

import java.util.List;

/**
 * Created by wnw on 2017/5/25.
 */

public interface IFindImagesByProductIdView {
    //显示进度条
    void showDialog();
    //展示用户数据
    void showProductImages(List<ProductImage> images);
}
