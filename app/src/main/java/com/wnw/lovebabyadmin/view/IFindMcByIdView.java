package com.wnw.lovebabyadmin.view;


import com.wnw.lovebabyadmin.domain.Mc;

/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindMcByIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showMc(Mc mc);
}
