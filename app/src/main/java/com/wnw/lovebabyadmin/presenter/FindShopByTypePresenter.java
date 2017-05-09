package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Shop;
import com.wnw.lovebabyadmin.model.imodel.IFindShopByType;
import com.wnw.lovebabyadmin.model.modelimp.FindShopByTypeImp;
import com.wnw.lovebabyadmin.view.IFindShopByTypeView;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public class FindShopByTypePresenter {
    private Context context;
    //view
    private IFindShopByTypeView findShopByTypeView;
    //model
    private IFindShopByType findShopByType = new FindShopByTypeImp();
    //ͨ通过构造函数传入view

    public FindShopByTypePresenter(Context context, IFindShopByTypeView findShopByTypeView) {
        this.context = context;
        this.findShopByTypeView = findShopByTypeView;
    }

    public void setView(IFindShopByTypeView findShopByTypeView){
        this.findShopByTypeView = findShopByTypeView;
    }

    //加载数据
    public void findShopByType(int type, int page) {
        //加载进度条
        findShopByTypeView.showDialog();
        //model进行数据获取
        if(findShopByType != null) {
            findShopByType.findShop(context, type, page, new IFindShopByType.ShopFindByTypeListener() {
                @Override
                public void complete(List<Shop> shops) {
                    if (findShopByTypeView != null){
                        findShopByTypeView.showShops(shops);
                    }
                }
            });
        }
    }
}
