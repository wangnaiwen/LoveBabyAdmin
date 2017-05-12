package com.wnw.lovebabyadmin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.activity.SortListActivity;
import com.wnw.lovebabyadmin.domain.Mc;

import java.util.List;

/**
 * Created by wnw on 2016/12/20.
 */

public class SortListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Mc> mcList;
    public static int mPosition;


    public SortListAdapter(Context context, List<Mc> mcs){
        this.mContext = context;
        this.mcList = mcs;
    }

    @Override
    public int getCount() {
        return mcList.size();
    }

    @Override
    public Object getItem(int i) {
        return mcList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SortListLvHolder sortListLvHolder = null;

        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_lv_sort_list, viewGroup,false);
            sortListLvHolder = new SortListLvHolder();
            sortListLvHolder.lvTitle = (TextView)view.findViewById(R.id.sort_list_lv_title);

            view.setTag(sortListLvHolder);
        }else {
            sortListLvHolder = (SortListLvHolder)view.getTag();
        }
        mPosition = i;
        sortListLvHolder.lvTitle.setText(mcList.get(i).getName());
        if(i == SortListActivity.mPosition){
            sortListLvHolder.lvTitle.setBackgroundColor(Color.parseColor("#b1f1efef"));
            sortListLvHolder.lvTitle.setTextColor(Color.parseColor("#fd4a7d"));
        }else{
            sortListLvHolder.lvTitle.setBackgroundColor(Color.WHITE);
            sortListLvHolder.lvTitle.setTextColor(Color.parseColor("#6b6868"));
        }
        return view;
    }

    private class SortListLvHolder{
        TextView lvTitle;
    }
}
