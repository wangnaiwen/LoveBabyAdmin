package com.wnw.lovebabyadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.domain.Shop;

import java.util.List;

/**
 * Created by wnw on 2017/5/9.
 */

public class ShopAdapter extends BaseAdapter{
    private Context context;
    private List<Shop> shopList;

    public ShopAdapter(Context context, List<Shop> shops) {
        this.context = context;
        this.shopList = shops;
    }

    public void setShopList(List<Shop> shopList){
        this.shopList = shopList;
    }

    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object getItem(int i) {
        return shopList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShopHolder shopHolder = null;
        if (view == null){
            shopHolder = new ShopHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_lv_shop, null);
            shopHolder.nameTv =(TextView)view.findViewById(R.id.tv_shop_name);
            shopHolder.idCardTv = (TextView)view.findViewById(R.id.tv_idcard);
            shopHolder.ownerTv = (TextView)view.findViewById(R.id.tv_owner);
            view.setTag(shopHolder);
        }else {
            shopHolder = (ShopHolder)view.getTag();
        }
        shopHolder.nameTv.setText("店铺名称："+shopList.get(i).getName());
        shopHolder.idCardTv.setText("身份证号："+shopList.get(i).getIdCard());
        shopHolder.ownerTv.setText("姓名："+shopList.get(i).getOwner());
        return view;
    }

    private class ShopHolder{
        TextView nameTv;
        TextView ownerTv;
        TextView idCardTv;
    }
}
