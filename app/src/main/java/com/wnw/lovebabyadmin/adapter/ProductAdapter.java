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
import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.util.TypeConverters;
import com.wnw.lovebabyadmin.view.custom.GoodsSquareLayout;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public class ProductAdapter extends BaseAdapter{
    private List<Product> productList;
    private Context mContext;

    public void setDatas(List<Product> products){
        this.productList = products;
    }

    public ProductAdapter(Context context, List<Product> products) {
        this.mContext = context;
        this.productList = products;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductHolder productHolder = null;
        if(convertView == null) {
            productHolder = new ProductHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gv_goods_item, null);
            productHolder.squareLayout = (GoodsSquareLayout) convertView.findViewById(R.id.goods_square_layout);
            productHolder.goodsCoverIcon = (ImageView)convertView.findViewById(R.id.goods_cover_icon);
            productHolder.titleTextView = (TextView)convertView.findViewById(R.id.goods_cover_title);
            productHolder.priceTextView = (TextView)convertView.findViewById(R.id.goods_cover_price);
            convertView.setTag(productHolder);
        }else{
            productHolder = (ProductHolder) convertView.getTag();
        }
        Glide.with(mContext).load(productList.get(position).getCoverImg()).into(productHolder.goodsCoverIcon);
        productHolder.titleTextView.setText(productList.get(position).getName());
        productHolder.priceTextView.setText(TypeConverters.LongConvertToString(productList.get(position).getRetailPrice()));
        return convertView;
    }

    private class ProductHolder{
        ImageView goodsCoverIcon;
        TextView titleTextView;
        TextView priceTextView;
        GoodsSquareLayout squareLayout;
    }
}
