package com.wnw.lovebabyadmin.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.activity.OrderDetailActivity;
import com.wnw.lovebabyadmin.adapter.MyOrderAdapter;
import com.wnw.lovebabyadmin.domain.Order;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.FindWaitSendOrderPresenter;
import com.wnw.lovebabyadmin.view.IFindWaitSendOrderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/27.
 */

public class WaitSendFragment extends Fragment implements AdapterView.OnItemClickListener,
        IFindWaitSendOrderView, View.OnClickListener, AbsListView.OnScrollListener{

    private ListView orderLv;
    private TextView noOrder;

    private MyOrderAdapter myOrderAdapter;
    private List<Order> orderList = new ArrayList<>();

    private FindWaitSendOrderPresenter findWaitSendOrderPresenter;

    private int page = 1;
    private boolean isEnd = false;

    private boolean isFirstTime = true;

    private View view;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = inflater.getContext();
        view = inflater.inflate(R.layout.fragment_wait_send, null);
        initView();
        initPresenter();
        startFindOrder();
        return view;
    }

    private void initView(){
        orderLv = (ListView)view.findViewById(R.id.lv_order);
        noOrder = (TextView)view.findViewById(R.id.null_order);

        orderLv.setOnItemClickListener(this);
        noOrder.setOnClickListener(this);
        orderLv.setOnScrollListener(this);
    }

    private void initPresenter(){
        findWaitSendOrderPresenter = new FindWaitSendOrderPresenter(context,this);
    }

    private void startFindOrder(){
        findWaitSendOrderPresenter.findWaitSendOrder(2,page);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_order:
                if(NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
                    Toast.makeText(context, "当前无网络", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(context, OrderDetailActivity.class);
                    intent.putExtra("order", orderList.get(i));
                    startActivity(intent);
                    ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        }
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
                    Toast.makeText(context,"已经到底了",Toast.LENGTH_SHORT).show();
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
    public void onResume() {
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
            myOrderAdapter = new MyOrderAdapter(context, orderList);
            orderLv.setAdapter(myOrderAdapter);
        }else{
            myOrderAdapter.setOrderList(orderList);
            myOrderAdapter.notifyDataSetChanged();
        }
    }
}

