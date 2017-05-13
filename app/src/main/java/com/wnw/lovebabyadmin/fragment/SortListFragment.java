package com.wnw.lovebabyadmin.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.activity.ProductListActivity;
import com.wnw.lovebabyadmin.adapter.SecondLevelAdapter;
import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.UpdateScPresenter;
import com.wnw.lovebabyadmin.view.IUpdateScView;

import java.util.List;

/**
 * Created by wnw on 2016/12/20.
 */

public class SortListFragment extends Fragment implements AdapterView.OnItemClickListener,
        IUpdateScView, AdapterView.OnItemLongClickListener{

    private Context context;
    private View mView;
    private LayoutInflater mInflater;

    private GridView secondLevelView;
    private SecondLevelAdapter secondLevelAdapter;
    private List<Sc> scList;

    private UpdateScPresenter updateScPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = inflater.getContext();
        mView = inflater.inflate(R.layout.fragment_sort_list, container, false);
        this.mInflater = inflater;
        initView();
        iniPresenter();
        return mView;
    }

    private void iniPresenter(){
        updateScPresenter = new UpdateScPresenter(context, this);
    }

    public void setScList(List<Sc> scs){
        this.scList = scs;
    }

    private void initView(){
        secondLevelView = (GridView)mView.findViewById(R.id.gv_second_level);
        secondLevelView.setOnItemClickListener(this);
        secondLevelView.setOnItemLongClickListener(this);
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

    private int position;
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.gv_second_level:
                position = i;
                name = scList.get(i).getName();
                showRenameMcDialog();
                break;
        }
        return true;
    }

    private String name ="";
    private void showRenameMcDialog(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_rename, null);
        final EditText editText = (EditText)view.findViewById(R.id.dialog_rename);
        editText.setText(name);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("更新名字")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name = editText.getText().toString().trim();
                        startUpdateSc();
                    }
                }).create();
        alertDialog.show();
    }


    private void startUpdateSc(){
        if (NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
            Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
        }else {
            Sc sc = scList.get(position);
            if (name.equals("")){
                Toast.makeText(context, "名字不能为空", Toast.LENGTH_SHORT).show();
            }else {
                sc.setName(name);
                updateScPresenter.updateSc(sc);
            }
        }
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(context);
            dialog.setMessage("正在努力中...");
        }
        dialog.show();
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showUpdateScResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(context, "名字更新成功", Toast.LENGTH_SHORT).show();
            scList.get(position).setName(name);
            secondLevelAdapter.setScList(scList);
            secondLevelAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(context, "名字更新失败", Toast.LENGTH_SHORT).show();
        }
    }
}
