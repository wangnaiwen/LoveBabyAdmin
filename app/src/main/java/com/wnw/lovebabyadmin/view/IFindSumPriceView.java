package com.wnw.lovebabyadmin.view;


import java.util.ArrayList;

/**
 * Created by wnw on 2017/5/27.
 */

public interface IFindSumPriceView {
    //显示进度条
    void showDialog();
    //展示用户数据
    void showSumPrice(ArrayList<Integer> days, ArrayList<Integer> months);
}
