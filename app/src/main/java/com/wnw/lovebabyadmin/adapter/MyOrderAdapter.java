package com.wnw.lovebabyadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.domain.Order;
import com.wnw.lovebabyadmin.util.TimeConvert;

import java.util.List;

/**
 * Created by wnw on 2017/4/17.
 */

public class MyOrderAdapter extends BaseAdapter {

    private Context context;
    private List<Order> orderList;

    public MyOrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orderList = orders;
    }

    public void setOrderList(List<Order> orderList){
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyOrderHolder myOrderHolder = null;
        if(view == null){
            myOrderHolder = new MyOrderHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_lv_order, null);
            myOrderHolder.typeTv = (TextView)view.findViewById(R.id.item_my_order_type);
            myOrderHolder.numberingTv = (TextView)view.findViewById(R.id.item_my_order_numbering);
            myOrderHolder.timeTv = (TextView)view.findViewById(R.id.item_my_order_create_time);
            view.setTag(myOrderHolder);
        }else {
            myOrderHolder = (MyOrderHolder)view.getTag();
        }

        myOrderHolder.timeTv.setText("创建时间："+ TimeConvert.getTime(orderList.get(i).getCreateTime()));
        myOrderHolder.numberingTv.setText("订单号："+orderList.get(i).getOrderNumber());
        int type = orderList.get(i).getOrderType();
        if(type == 1){
            myOrderHolder.typeTv.setText("待支付");
        }else if(type == 2){
            myOrderHolder.typeTv.setText("待发货");
        }else if(type == 3){
            myOrderHolder.typeTv.setText("待收货");
        }else if(type == 4){
            myOrderHolder.typeTv.setText("待评价");
        }else if(type == 5){
            myOrderHolder.typeTv.setText("已完成");
        }
        return view;
    }


    class MyOrderHolder{
        TextView typeTv;
        TextView numberingTv;
        TextView timeTv;
    }
}
