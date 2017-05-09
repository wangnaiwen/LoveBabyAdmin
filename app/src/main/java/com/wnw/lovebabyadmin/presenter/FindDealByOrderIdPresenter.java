package com.wnw.lovebabyadmin.presenter;

import android.content.Context;


import com.wnw.lovebabyadmin.domain.Deal;
import com.wnw.lovebabyadmin.model.imodel.IFindDealByOrderIdModel;
import com.wnw.lovebabyadmin.model.modelimp.FindDealByOrderIdModelImpl;
import com.wnw.lovebabyadmin.view.IFindDealByOrderIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindDealByOrderIdPresenter {
    private Context context;
    //view
    private IFindDealByOrderIdView findDealByOrderIdView;
    //model
    private IFindDealByOrderIdModel findDealByOrderIdModel = new FindDealByOrderIdModelImpl();
    //ͨ通过构造函数传入view

    public FindDealByOrderIdPresenter(Context context, IFindDealByOrderIdView findDealByOrderIdView) {
        this.context = context;
        this.findDealByOrderIdView = findDealByOrderIdView;
    }

    public void setFindDealByOrderIdView(IFindDealByOrderIdView findDealByOrderIdView){
        this.findDealByOrderIdView = findDealByOrderIdView;
    }

    //加载数据
    public void findDealByOrderId(int orderId) {
        //加载进度条
        findDealByOrderIdView.showDialog();
        //model进行数据获取
        if(findDealByOrderIdModel != null) {
            findDealByOrderIdModel.findDealByOrderId(context, orderId, new IFindDealByOrderIdModel.DealFindByOrderIdListener() {
                @Override
                public void complete(List<Deal> deals) {
                    findDealByOrderIdView.showDealsByOrderId(deals);
                }
            });
        }
    }
}
