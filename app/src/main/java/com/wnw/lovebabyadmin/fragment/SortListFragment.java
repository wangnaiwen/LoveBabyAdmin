package com.wnw.lovebabyadmin.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.activity.ProductListActivity;
import com.wnw.lovebabyadmin.adapter.SecondLevelAdapter;
import com.wnw.lovebabyadmin.domain.Sc;

import java.util.List;

/**
 * Created by wnw on 2016/12/20.
 */

public class SortListFragment extends Fragment implements AdapterView.OnItemClickListener{

    private Context context;
    private View mView;
    private LayoutInflater mInflater;

    private GridView secondLevelView;
    private SecondLevelAdapter secondLevelAdapter;
    private List<Sc> scList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = inflater.getContext();
        mView = inflater.inflate(R.layout.fragment_sort_list, container, false);
        this.mInflater = inflater;
        initView();
        return mView;
    }

    public void setScList(List<Sc> scs){
        this.scList = scs;
    }

    private void initView(){
        secondLevelView = (GridView)mView.findViewById(R.id.gv_second_level);
        secondLevelView.setOnItemClickListener(this);
        setAdapter();
    }

    private void setAdapter(){
        if(secondLevelAdapter == null){
            secondLevelAdapter = new SecondLevelAdapter(mInflater.getContext(), scList);
            secondLevelView.setAdapter(secondLevelAdapter);
        }else {
            secondLevelAdapter.setScList(scList);
            secondLevelAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.gv_second_level:
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("sc", scList.get(i));
                startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }
}
