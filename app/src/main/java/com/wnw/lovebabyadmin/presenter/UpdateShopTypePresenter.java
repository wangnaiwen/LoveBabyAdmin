package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.model.imodel.IUpdateShopType;
import com.wnw.lovebabyadmin.model.modelimp.UpdateShopTypeImp;
import com.wnw.lovebabyadmin.view.IUpdateShopTypeView;

/**
 * Created by wnw on 2017/5/8.
 */

public class UpdateShopTypePresenter {
    private Context context;
    //view
    private IUpdateShopTypeView updateShopTypeView;
    //model
    private IUpdateShopType updateShopType = new UpdateShopTypeImp();
    //ͨ通过构造函数传入view

    public UpdateShopTypePresenter(Context context, IUpdateShopTypeView updateShopTypeView) {
        this.context = context;
        this.updateShopTypeView = updateShopTypeView;
    }

    public void setView(IUpdateShopTypeView updateShopTypeView){
        this.updateShopTypeView = updateShopTypeView;
    }

    //加载数据
    public void updateShopType(int id, int type) {
        //加载进度条
        updateShopTypeView.showDialog();
        //model进行数据获取
        if(updateShopType != null) {
            updateShopType.updateShopType(context, id, type, new IUpdateShopType.ShopUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (updateShopTypeView != null){
                        updateShopTypeView.showUpdateShopResult(isSuccess);
                    }
                }
            });
        }
    }
}
