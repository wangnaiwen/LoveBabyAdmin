package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Order;
import com.wnw.lovebabyadmin.model.imodel.IFindWaitSendOrder;
import com.wnw.lovebabyadmin.model.modelimp.FindWaitSendOrderImp;
import com.wnw.lovebabyadmin.view.IFindWaitSendOrderView;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public class FindWaitSendOrderPresenter {
    private Context context;
    //view
    private IFindWaitSendOrderView findWaitSendOrderView;
    //model
    private IFindWaitSendOrder findWaitSendOrder = new FindWaitSendOrderImp();
    //ͨ通过构造函数传入view

    public FindWaitSendOrderPresenter(Context context, IFindWaitSendOrderView findWaitSendOrderView) {
        this.context = context;
        this.findWaitSendOrderView = findWaitSendOrderView;
    }

    public void setView(IFindWaitSendOrderView findWaitSendOrderView){
        this.findWaitSendOrderView = findWaitSendOrderView;
    }

    //加载数据
    public void findWaitSendOrder(int type, int page) {
        //加载进度条
        findWaitSendOrderView.showDialog();
        //model进行数据获取
        if(findWaitSendOrder != null) {
            findWaitSendOrder.findWaitOrder(context, type, page, new IFindWaitSendOrder.WaitOrderFindListener() {
                @Override
                public void complete(List<Order> orders,  List<String> names) {
                    if (findWaitSendOrderView != null){
                        findWaitSendOrderView.showOrders(orders, names);
                    }
                }
            });
        }
    }
}
