package com.wnw.lovebabyadmin.activity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.adapter.ProductAdapter;
import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.FindProductByKeyPresenter;
import com.wnw.lovebabyadmin.view.IFindProductByKeyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/8.
 */

public class SearchResultActivity extends Activity implements View.OnClickListener,
        IFindProductByKeyView, AdapterView.OnItemClickListener{

    private ImageView searchRelBack;
    private GridView productGv;
    private TextView noProductTv;

    private int userId;
    private String keyWord;

    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter = null;

    private FindProductByKeyPresenter findProductByKeyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getKeyWord();
        initView();
        initPresenter();
        startFindProduct();
    }

    private void getKeyWord(){
        Intent intent = getIntent();
        keyWord = intent.getStringExtra("keyWord");
        userId = intent.getIntExtra("userId", userId);
    }

    private void initView(){
        searchRelBack = (ImageView) findViewById(R.id.search_result_back);
        productGv = (GridView)findViewById(R.id.gv_product_list);
        noProductTv = (TextView)findViewById(R.id.tv_null);
        searchRelBack.setOnClickListener(this);

        noProductTv.setOnClickListener(this);
        productGv.setOnItemClickListener(this);
    }

    private void initPresenter(){
        findProductByKeyPresenter = new FindProductByKeyPresenter(this,this);
    }

    private void startFindProduct(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "暂无网络", Toast.LENGTH_SHORT).show();
            noProductTv.setVisibility(View.VISIBLE);
            productGv.setVisibility(View.GONE);
        }else {
            findProductByKeyPresenter.findProductByKey(keyWord, userId);
        }
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setMessage("订单正在提交中...");
        }
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showProductByUserId(List<Product> products) {
        dismissDialogs();
        if(products == null){
            noProductTv.setVisibility(View.VISIBLE);
            productGv.setVisibility(View.GONE);
        }else {
            this.productList = products;
            if(productList.size() == 0){
                noProductTv.setVisibility(View.VISIBLE);
                productGv.setVisibility(View.GONE);
            }else {
                noProductTv.setVisibility(View.GONE);
                productGv.setVisibility(View.VISIBLE);
                setProductAdapter();
            }
        }
    }

    private void setProductAdapter(){
        if(productAdapter == null){
            productAdapter = new ProductAdapter(this, productList);
            productGv.setAdapter(productAdapter);
        }else {
            productAdapter.setDatas(productList);
            productAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_result_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.tv_null:
                startFindProduct();
                break;
            default:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.gv_product_list:
                Intent intent = new Intent(this, EditProductActivity.class);
                intent.putExtra("product", productList.get(i));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        findProductByKeyPresenter.setView(null);
    }
}
