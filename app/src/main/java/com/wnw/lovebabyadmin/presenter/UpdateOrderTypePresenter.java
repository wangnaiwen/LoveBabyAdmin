package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.model.imodel.IUpdateOrderType;
import com.wnw.lovebabyadmin.model.modelimp.UpdateOrderTypeImp;
import com.wnw.lovebabyadmin.view.IUpdateOrderTypeView;

/**
 * Created by wnw on 2017/5/8.
 */

public class UpdateOrderTypePresenter {
    private Context context;
    //view
    private IUpdateOrderTypeView updateOrderTypeView;
    //model
    private IUpdateOrderType updateOrderType = new UpdateOrderTypeImp();
    //ͨ通过构造函数传入view

    public UpdateOrderTypePresenter(Context context,IUpdateOrderTypeView updateOrderTypeView) {
        this.context = context;
        this.updateOrderTypeView = updateOrderTypeView;
    }

    public void setView(IUpdateOrderTypeView updateOrderTypeView) {
        this.updateOrderTypeView = updateOrderTypeView;
    }

    //加载数据
    public void updateOrderType(int id, int type) {
        //加载进度条
        updateOrderTypeView.showDialog();
        //model进行数据获取
        if (updateOrderType != null) {
            updateOrderType.updateOrderType(context, id, type, new IUpdateOrderType.OrderUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(updateOrderTypeView != null){
                        updateOrderTypeView.showUpdateOrderResult(isSuccess);
                    }
                }
            });
        }
    }
}
