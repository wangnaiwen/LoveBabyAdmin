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
import com.wnw.lovebabyadmin.adapter.MyOrderAdapter;
import com.wnw.lovebabyadmin.domain.Order;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.FindWaitSendOrderPresenter;
import com.wnw.lovebabyadmin.view.IFindWaitSendOrderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public class SendOrderActivity extends Activity implements AdapterView.OnItemClickListener,
        IFindWaitSendOrderView, View.OnClickListener, AbsListView.OnScrollListener{
    private ListView orderLv;
    private TextView noOrder;
    private ImageView back;

    private MyOrderAdapter myOrderAdapter;
    private List<Order> orderList = new ArrayList<>();

    private FindWaitSendOrderPresenter findWaitSendOrderPresenter;

    private int page = 1;
    private boolean isEnd = false;

    private boolean isFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        initPresenter();
        startFindOrder();
    }

    private void initView(){
        orderLv = (ListView)findViewById(R.id.lv_order);
        noOrder = (TextView)findViewById(R.id.null_order);
        back = (ImageView)findViewById(R.id.back);

        back.setOnClickListener(this);
        orderLv.setOnItemClickListener(this);
        noOrder.setOnClickListener(this);
        orderLv.setOnScrollListener(this);
    }

    private void initPresenter(){
        findWaitSendOrderPresenter = new FindWaitSendOrderPresenter(this,this);
    }

    private void startFindOrder(){
        findWaitSendOrderPresenter.findWaitSendOrder(2,page);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_order:
                if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
                    Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(this, OrderDetailActivity.class);
                    intent.putExtra("order", orderList.get(i));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.null_order:
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
                    Toast.makeText(SendOrderActivity.this,"已经到底了",Toast.LENGTH_SHORT).show();
                }else {
                    startFindOrder();
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
    public void showOrders(List<Order> orders) {
        dismissDialogs();
        if (orders == null){
            noOrder.setVisibility(View.VISIBLE);
            orderLv.setVisibility(View.GONE);
        }else {
            Log.d("www", orders.size()+"");
            if (orders.size() == 0){
                noOrder.setVisibility(View.VISIBLE);
                orderLv.setVisibility(View.GONE);
            }else {
                orderLv.setVisibility(View.VISIBLE);
                noOrder.setVisibility(View.GONE);
                page ++;
                if(orders.size() < 10){  //到底
                    isEnd = true;
                }
                orderList.addAll(orders);
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
        orderList.clear();
        startFindOrder();
    }

    private void setAdapter(){
        if (myOrderAdapter == null){
            myOrderAdapter = new MyOrderAdapter(this, orderList);
            orderLv.setAdapter(myOrderAdapter);
        }else{
            myOrderAdapter.setOrderList(orderList);
            myOrderAdapter.notifyDataSetChanged();
        }
    }
}
