package com.wnw.lovebabyadmin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.domain.ProductImage;

import java.util.List;

/**
 * Created by wnw on 2017/5/24.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Bitmap> imageList;

    public ImageAdapter(Context context, List<Bitmap> images) {
        this.context = context;
        this.imageList = images;
    }

    public void setImageList(List<Bitmap> images){
        this.imageList = images;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int i) {
        return imageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageHolder imageHolder = null;
        if(view == null){
            imageHolder = new ImageHolder();
            view = LayoutInflater.from(context).inflate(R.layout.lv_item_image, null);
            imageHolder.imageView = (ImageView) view.findViewById(R.id.img);
            view.setTag(imageHolder);
        }else {
            imageHolder = (ImageHolder)view.getTag();
        }
        imageHolder.imageView.setImageBitmap(imageList.get(i));
        return view;
    }


    class ImageHolder{
        ImageView imageView;
    }
}
