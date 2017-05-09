package com.wnw.lovebabyadmin.view;


import com.wnw.lovebabyadmin.domain.Deal;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindDealByOrderIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showDealsByOrderId(List<Deal> deals);
}
