package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.model.imodel.IFindSpecialPriceModel;
import com.wnw.lovebabyadmin.model.modelimp.FindSpecialPriceModelImpl;
import com.wnw.lovebabyadmin.view.IFindSpecialPriceView;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public class FindSpecialPricePresenter {
    private  Context context;
    //view
    private IFindSpecialPriceView specialPriceView;
    //model
    private IFindSpecialPriceModel findSpecialPriceModel = new FindSpecialPriceModelImpl();
    //ͨ通过构造函数传入view

    public FindSpecialPricePresenter(Context context, IFindSpecialPriceView specialPriceView) {
        super();
        this.context = context;
        this.specialPriceView = specialPriceView;
    }

    public void setSpecialPriceView(IFindSpecialPriceView specialPriceView){
        this.specialPriceView = specialPriceView;
    }

    //加载数据
    public void load() {
        //加载进度条
        specialPriceView.showLoading();
        //model进行数据获取
        if(findSpecialPriceModel != null){
            findSpecialPriceModel.findSpecialPrice(context, new IFindSpecialPriceModel.FindSpecialPriceListener() {
                @Override
                public void complete(List<Product> productList) {
                    if(specialPriceView != null){
                        specialPriceView.showSpecialPrice(productList);
                    }
                }
            });

        }
    }
}
