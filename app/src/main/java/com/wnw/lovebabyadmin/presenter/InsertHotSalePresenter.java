package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.HotSale;
import com.wnw.lovebabyadmin.model.imodel.IInsertHotSale;
import com.wnw.lovebabyadmin.model.modelimp.InsertHotSaleImp;
import com.wnw.lovebabyadmin.view.IInsertHotSaleView;

/**
 * Created by wnw on 2017/5/25.
 */

public class InsertHotSalePresenter {
    private Context context;
    //view
    private IInsertHotSaleView insertHotSaleView;
    //model
    private IInsertHotSale insertHotSale = new InsertHotSaleImp();
    //ͨ通过构造函数传入view

    public InsertHotSalePresenter(Context context, IInsertHotSaleView insertHotSaleView) {
        this.context = context;
        this.insertHotSaleView = insertHotSaleView;
    }

    public void setView(IInsertHotSaleView insertHotSaleView){
        this.insertHotSaleView = insertHotSaleView;
    }

    //加载数据
    public void insertHotSale(final HotSale hotSale) {
        //加载进度条
        insertHotSaleView.showDialog();
        //model进行数据获取
        if(insertHotSale != null) {
            insertHotSale.insertHotSale(context, hotSale, new IInsertHotSale.InsertHotSaleListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (insertHotSaleView != null){
                        insertHotSaleView.showInsertHotSaleResult(isSuccess);
                    }
                }
            });
        }
    }
}
