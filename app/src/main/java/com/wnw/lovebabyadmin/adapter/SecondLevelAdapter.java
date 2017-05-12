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
import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.view.custom.GoodsSquareLayout;

import java.util.List;

/**
 * Created by wnw on 2016/12/21.
 */

public class SecondLevelAdapter extends BaseAdapter {

    private Context context;
    private List<Sc> scList;

    public SecondLevelAdapter(Context context, List<Sc> scs){
        this.context = context;
        this.scList = scs;
    }

    public void setScList(List<Sc> scs){
        this.scList = scs;
    }

    @Override
    public int getCount() {
        return scList.size();
    }

    @Override
    public Object getItem(int i) {
        return scList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ScHolder scHolder = null;
        if(view == null){
            scHolder = new ScHolder();
            view = LayoutInflater.from(context).inflate(R.layout.gv_second_level_item, viewGroup, false);
            scHolder.squareLayout = (GoodsSquareLayout)view.findViewById(R.id.second_level_square_layout);
            scHolder.title = (TextView)view.findViewById(R.id.second_level_cover_title);
            scHolder.titleImg = (ImageView) view.findViewById(R.id.second_level_cover_icon);
            view.setTag(scHolder);
        }else {
            scHolder = (ScHolder)view.getTag();
        }
        Glide.with(context).load(scList.get(i).getImage()).into(scHolder.titleImg);
        scHolder.title.setText(scList.get(i).getName());

        return view;
    }

    private class ScHolder{
        GoodsSquareLayout squareLayout;
        ImageView titleImg;
        TextView title;
    }
}
