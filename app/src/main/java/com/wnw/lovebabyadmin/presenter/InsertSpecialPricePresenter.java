package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.SpecialPrice;
import com.wnw.lovebabyadmin.model.imodel.IInsertSpecialPrice;
import com.wnw.lovebabyadmin.model.modelimp.InsertSpecialPriceImp;
import com.wnw.lovebabyadmin.view.IInsertSpecialPriceView;

/**
 * Created by wnw on 2017/5/25.
 */

public class InsertSpecialPricePresenter {
    private Context context;
    //view
    private IInsertSpecialPriceView insertSpecialPriceView;
    //model
    private IInsertSpecialPrice insertSpecialPrice = new InsertSpecialPriceImp();
    //ͨ通过构造函数传入view

    public InsertSpecialPricePresenter(Context context, IInsertSpecialPriceView insertSpecialPriceView) {
        this.context = context;
        this.insertSpecialPriceView = insertSpecialPriceView;
    }

    public void setView(IInsertSpecialPriceView insertSpecialPriceView){
        this.insertSpecialPriceView = insertSpecialPriceView;
    }

    //加载数据
    public void insertSpecialPrice(SpecialPrice specialPrice) {
        //加载进度条
        insertSpecialPriceView.showDialog();
        //model进行数据获取
        if(insertSpecialPrice != null) {
            insertSpecialPrice.insertSpecialPrice(context, specialPrice, new IInsertSpecialPrice.InsertSpecialPriceListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (insertSpecialPriceView != null){
                        insertSpecialPriceView.showInsertSpecialPriceResult(isSuccess);
                    }
                }
            });
        }
    }
}
