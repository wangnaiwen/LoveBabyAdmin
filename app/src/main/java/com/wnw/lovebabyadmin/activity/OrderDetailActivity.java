package com.wnw.lovebabyadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.adapter.OrderLvAdapter;
import com.wnw.lovebabyadmin.bean.ShoppingCarItem;
import com.wnw.lovebabyadmin.domain.Deal;
import com.wnw.lovebabyadmin.domain.Order;
import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.domain.ReceAddress;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.FindDealByOrderIdPresenter;
import com.wnw.lovebabyadmin.presenter.FindProductByIdPresenter;
import com.wnw.lovebabyadmin.presenter.FindReceAddressByIdPresenter;
import com.wnw.lovebabyadmin.presenter.UpdateOrderTypePresenter;
import com.wnw.lovebabyadmin.util.OrderTypeConvert;
import com.wnw.lovebabyadmin.util.TimeConvert;
import com.wnw.lovebabyadmin.util.TypeConverters;
import com.wnw.lovebabyadmin.view.IFindDealByOrderIdView;
import com.wnw.lovebabyadmin.view.IFindProductByIdView;
import com.wnw.lovebabyadmin.view.IFindReceAddressByIdView;
import com.wnw.lovebabyadmin.view.IUpdateOrderTypeView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wnw on 2017/4/17.
 */

public class OrderDetailActivity extends Activity implements View.OnClickListener,
        IUpdateOrderTypeView, IFindDealByOrderIdView,
        IFindReceAddressByIdView, IFindProductByIdView {

    private ImageView back;           //返回键
    private TextView orderTypeTv;     //订单状态
    private TextView orderNumberTv;   //订单号
    private TextView createTimeTv;    //创建时间
    private TextView payTimeTv;       //付款时间
    private TextView nameTv;          //收货人姓名
    private TextView phoneTv;         //收货人联系方式
    private TextView addressTv;       //收货人地址
    private ListView dealLv;          //交易列表
    private TextView sumPriceTv;      //总价
    private TextView confirmSendTv;//确认收货

    private Order order;             //当前的Order
    private List<Deal> dealList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private List<ShoppingCarItem> shoppingCarItemList = new ArrayList<>();

    private FindDealByOrderIdPresenter findDealByOrderIdPresenter;
    private UpdateOrderTypePresenter updateOrderPresenter;
    private FindReceAddressByIdPresenter findReceAddressByIdPresenter;
    private FindProductByIdPresenter findProductByIdPresenter;

    private OrderLvAdapter orderLvAdapter;

    private int sumPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        getOrder();
        initPresenter();
        startFindDeals();
        startFindAddress();
        initView();
    }

    private void getOrder(){
        Intent intent = getIntent();
        order = (Order)intent.getSerializableExtra("order");
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back_my_order_detail);
        orderTypeTv = (TextView)findViewById(R.id.tv_order_type);
        orderNumberTv = (TextView)findViewById(R.id.tv_order_number);
        createTimeTv = (TextView)findViewById(R.id.tv_order_create_time);
        payTimeTv = (TextView)findViewById(R.id.tv_order_pay_time);
        nameTv = (TextView)findViewById(R.id.tv_order_name);
        phoneTv = (TextView)findViewById(R.id.tv_order_phone);
        addressTv = (TextView)findViewById(R.id.tv_order_address);
        sumPriceTv = (TextView)findViewById(R.id.tv_order_all_price);
        dealLv = (ListView) findViewById(R.id.lv_order_deal);
        confirmSendTv = (TextView)findViewById(R.id.tv_confirm_send);

        back.setOnClickListener(this);
        confirmSendTv.setOnClickListener(this);

        //设初始值
        int orderType = order.getOrderType();

        orderTypeTv.setText(OrderTypeConvert.getStringType(orderType));
        orderNumberTv.setText("订单号:"+order.getOrderNumber());
        createTimeTv.setText("创建时间:"+ TimeConvert.getTime(order.getCreateTime()));
        payTimeTv.setText("支付时间："+TimeConvert.getTime(order.getPayTime()));

    }

    private void initPresenter(){
        findDealByOrderIdPresenter = new FindDealByOrderIdPresenter(this,this);
        updateOrderPresenter = new UpdateOrderTypePresenter(this,this);
        findReceAddressByIdPresenter = new FindReceAddressByIdPresenter(this, this);
        findProductByIdPresenter = new FindProductByIdPresenter(this,this);
    }

    private void startFindDeals(){
        findDealByOrderIdPresenter.findDealByOrderId(order.getId());
    }

    private void startUpdateOrder(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();
        }else{
            updateOrderPresenter.updateOrderType(order.getId(), order.getOrderType());
        }
    }
    private void startFindAddress(){
        findReceAddressByIdPresenter.findReceAddresById(order.getAddressId());
    }

    private int position = 0;
    private void startFindProduct(){
        findProductByIdPresenter.findProductById(dealList.get(position).getProductId());
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
    public void showDealsByOrderId(List<Deal> deals) {
        if(deals == null){
            Toast.makeText(this, "加载不出来", Toast.LENGTH_SHORT).show();
        }else{
            this.dealList = deals;
            startFindProduct();
        }
    }

    @Override
    public void showProduct(Product product) {
        if(product == null){
            Toast.makeText(this, "加载不出来", Toast.LENGTH_SHORT).show();
        }else{
            productList.add(product);
            position ++;
            if(position != dealList.size()){
                startFindProduct();
            }else{
                //已经全部查出来了，开始组装ShoppingCatItem，并且设置Adapter
                setAdapter();
                dismissDialogs();
            }
        }
    }

    @Override
    public void showUpdateOrderResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){
            Toast.makeText(this, "发货成功", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else{
            Toast.makeText(this, "发货失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showReceAddressFindById(ReceAddress address) {
        if(address != null){
            nameTv.setText(address.getReceiver());
            phoneTv.setText(address.getPhone());
            addressTv.setText(address.getProvince()+address.getCity()+address.getCounty()+address.getDetailAddress());
        }else {
            Toast.makeText(this, "地址加载不出来", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter(){
        if(dealList.size() != productList.size()){
            Toast.makeText(this, "加载不出来", Toast.LENGTH_SHORT).show();
        }else{
            int length = dealList.size();
            for(int i = 0; i < length; i++){
                ShoppingCarItem shoppingCarItem = new ShoppingCarItem();
                shoppingCarItem.setProductId(productList.get(i).getId());
                shoppingCarItem.setGoodsNum(dealList.get(i).getProductCount());
                shoppingCarItem.setGoodsImg(productList.get(i).getCoverImg());
                shoppingCarItem.setGoodsPrice((int)productList.get(i).getRetailPrice());
                shoppingCarItem.setGoodsTitle(productList.get(i).getName());

                shoppingCarItemList.add(shoppingCarItem);

                sumPrice = sumPrice + (int)dealList.get(i).getSumPrice();
            }

            orderLvAdapter = new OrderLvAdapter(this, shoppingCarItemList);
            dealLv.setAdapter(orderLvAdapter);
            sumPriceTv.setText("总价格:" + TypeConverters.IntConvertToString(sumPrice));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_my_order_detail:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_confirm_send:
                order.setOrderType(3);
                startUpdateOrder();
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
    protected void onDestroy() {
        super.onDestroy();
        findDealByOrderIdPresenter.setFindDealByOrderIdView(null);
        findProductByIdPresenter.setView(null);
        updateOrderPresenter.setView(null);
        findReceAddressByIdPresenter.setFindReceAddressByIdView(null);
    }
}
