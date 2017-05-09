package com.wnw.lovebabyadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.adapter.ShopAdapter;
import com.wnw.lovebabyadmin.domain.Shop;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.FindShopByTypePresenter;
import com.wnw.lovebabyadmin.view.IFindShopByTypeView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public class ReviewStoreActivity extends Activity implements AdapterView.OnItemClickListener,
        IFindShopByTypeView, View.OnClickListener, AbsListView.OnScrollListener{
    private ListView shopLv;
    private TextView noShop;
    private ImageView back;

    private ShopAdapter shopAdapter;
    private List<Shop> shopList = new ArrayList<>();

    private FindShopByTypePresenter findShopByTypePresenter;

    private int page = 1;
    private boolean isEnd = false;

    private boolean isFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initView();
        initPresenter();
        startFindShop();
    }

    private void initView(){
        shopLv = (ListView)findViewById(R.id.lv_shop);
        noShop = (TextView)findViewById(R.id.null_shopr);
        back = (ImageView)findViewById(R.id.back);

        back.setOnClickListener(this);
        shopLv.setOnItemClickListener(this);
        noShop.setOnClickListener(this);
        shopLv.setOnScrollListener(this);
    }

    private void initPresenter(){
        findShopByTypePresenter = new FindShopByTypePresenter(this,this);
    }

    private void startFindShop(){
        findShopByTypePresenter.findShopByType(1,page);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_shop:
                if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
                    Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(this, ShopDetailActivity.class);
                    intent.putExtra("shop", shopList.get(i));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.null_shopr:
                reFindOrder();
                break;
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        // 当不滚动时
        if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                //加载更多功能的代码
                //监听ListView滑动到底部，加载更多评论
                if(isEnd){  //到底
                    Toast.makeText(ReviewStoreActivity.this,"已经到底了",Toast.LENGTH_SHORT).show();
                }else {
                    startFindShop();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(this);
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
    public void showShops(List<Shop> shops) {
        dismissDialogs();
        if (shops == null){
            noShop.setVisibility(View.VISIBLE);
            shopLv.setVisibility(View.GONE);
        }else {
            if (shops.size() == 0){
                noShop.setVisibility(View.VISIBLE);
                shopLv.setVisibility(View.GONE);
            }else {
                shopLv.setVisibility(View.VISIBLE);
                noShop.setVisibility(View.GONE);
                page ++;
                if(shops.size() < 10){  //到底
                    isEnd = true;
                }
                shopList.addAll(shops);
                setAdapter();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstTime){
            isFirstTime = false;
        }else {
            reFindOrder();
        }
    }

    private void reFindOrder(){
        isEnd = false;
        page = 1;
        shopList.clear();
        startFindShop();
    }

    private void setAdapter(){
        if (shopAdapter == null){
            shopAdapter = new ShopAdapter(this, shopList);
            shopLv.setAdapter(shopAdapter);
        }else{
            shopAdapter.setShopList(shopList);
            shopAdapter.notifyDataSetChanged();
        }
    }
}
