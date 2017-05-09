package com.wnw.lovebabyadmin.view;

import com.wnw.lovebabyadmin.domain.Shop;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IFindShopByTypeView {
    void showDialog();
    void showShops(List<Shop> shops);
}
