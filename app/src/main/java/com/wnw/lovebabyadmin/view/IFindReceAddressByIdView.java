package com.wnw.lovebabyadmin.view;


import com.wnw.lovebabyadmin.domain.ReceAddress;

/**
 * Created by wnw on 2017/4/18.
 */

public interface IFindReceAddressByIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showReceAddressFindById(ReceAddress address);
}
