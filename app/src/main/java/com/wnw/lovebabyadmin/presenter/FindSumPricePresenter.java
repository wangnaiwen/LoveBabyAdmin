package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.model.imodel.IFindSumPrice;
import com.wnw.lovebabyadmin.model.modelimp.FindSumPriceImp;
import com.wnw.lovebabyadmin.view.IFindSumPriceView;

import java.util.ArrayList;

/**
 * Created by wnw on 2017/5/27.
 */

public class FindSumPricePresenter {
    private Context context;
    //view
    private IFindSumPriceView findSumPriceView;
    //model
    private IFindSumPrice findSumPrice = new FindSumPriceImp();
    //ͨ通过构造函数传入view

    public FindSumPricePresenter(Context context, IFindSumPriceView findSumPriceView) {
        super();
        this.context = context;
        this.findSumPriceView = findSumPriceView;
    }

    public void setView(IFindSumPriceView findSumPriceView){
        this.findSumPriceView = findSumPriceView;
    }

    //加载数据
    public void findSumPrice() {
        //加载进度条
        findSumPriceView.showDialog();
        //model进行数据获取
        if(findSumPrice != null){
            findSumPrice.findSumPrice(context, new IFindSumPrice.FindSumPriceListener() {
                @Override
                public void complete(ArrayList<Integer> days, ArrayList<Integer> months) {
                    if (findSumPriceView != null){
                        findSumPriceView.showSumPrice(days, months);
                    }
                }
            });

        }
    }
}
