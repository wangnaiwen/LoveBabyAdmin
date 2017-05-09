package com.wnw.lovebabyadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.bean.ShoppingCarItem;
import com.wnw.lovebabyadmin.util.TypeConverters;

import java.util.List;

/**
 * Created by wnw on 2016/12/11.
 */

public class OrderLvAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingCarItem> shoppingCarItemList;

    public OrderLvAdapter(Context context, List<ShoppingCarItem> shoppingCarItems){
        this.context = context;
        this.shoppingCarItemList = shoppingCarItems;
    }

    public void setShoppingCarItemList(List<ShoppingCarItem> shoppingCarItems){
        this.shoppingCarItemList = shoppingCarItems;
    }

    @Override
    public int getCount() {
        return shoppingCarItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return shoppingCarItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OrderLvHolder orderLvHolder = null;
        if(view == null){
            orderLvHolder = new OrderLvHolder();
            view = LayoutInflater.from(context).inflate(R.layout.order_lv_item , null);
            orderLvHolder.orderIcon = (ImageView)view.findViewById(R.id.order_img);
            orderLvHolder.orderTitle = (TextView)view.findViewById(R.id.order_title);
            orderLvHolder.unitPrice = (TextView)view.findViewById(R.id.order_unit_price);
            orderLvHolder.goodsNum = (TextView)view.findViewById(R.id.goods_num);
            view.setTag(orderLvHolder);
        }else {
            orderLvHolder = (OrderLvHolder)view.getTag();
        }

        ShoppingCarItem shoppingCarItem = shoppingCarItemList.get(i);
        Glide.with(context).load(shoppingCarItem.getGoodsImg()).into(orderLvHolder.orderIcon);
        orderLvHolder.orderTitle.setText(shoppingCarItem.getGoodsTitle());
        TypeConverters typeConverters = new TypeConverters();
        orderLvHolder.unitPrice.setText(typeConverters.IntConvertToString(shoppingCarItem.getGoodsPrice()));
        orderLvHolder.goodsNum.setText(shoppingCarItem.getGoodsNum()+"");
        return view;
    }

    private class OrderLvHolder{
        ImageView orderIcon;
        TextView orderTitle;
        TextView unitPrice;
        TextView goodsNum;
    }
}
