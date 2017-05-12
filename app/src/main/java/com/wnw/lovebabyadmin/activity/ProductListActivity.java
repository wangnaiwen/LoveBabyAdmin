package com.wnw.lovebabyadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.adapter.ProductAdapter;
import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.FindProductByScIdPresenter;
import com.wnw.lovebabyadmin.view.IFindProductByScIdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public class ProductListActivity extends Activity implements AdapterView.OnItemClickListener,
        AdapterView.OnClickListener, IFindProductByScIdView, AbsListView.OnScrollListener{

    private GridView productGv;
    private ImageView back;
    private TextView scNameTv;
    private TextView noProductTv;

    private Sc sc;

    private int page = 1; //当前是第一页
    private boolean isEnd = false;  //是否是最后一页，已经加载完

    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private FindProductByScIdPresenter findProductByScIdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getSc();
        initView();
        initPresenter();
        startFindProducts();
    }

    private void getSc(){
        Intent intent = getIntent();
        sc = (Sc)intent.getSerializableExtra("sc");
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back_product_list);
        scNameTv = (TextView)findViewById(R.id.tv_sc_name);
        productGv = (GridView)findViewById(R.id.gv_product_list);
        noProductTv = (TextView)findViewById(R.id.tv_no_product);

        noProductTv.setOnClickListener(this);
        back.setOnClickListener(this);
        productGv.setOnItemClickListener(this);

        scNameTv.setText(sc.getName());
        productGv.setOnScrollListener(this);
    }

    private void initPresenter(){
        findProductByScIdPresenter = new FindProductByScIdPresenter(this,this);
    }

    private void startFindProducts(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "暂无网络", Toast.LENGTH_SHORT).show();
            noProductTv.setVisibility(View.VISIBLE);
            productGv.setVisibility(View.GONE);
        }else {
            findProductByScIdPresenter.findProductByScId(sc.getId(), page);
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
    public void showProducts(List<Product> products) {
        dismissDialogs();
        page ++;
        if(products != null){
            if(products.size() < 10){  //一次加载10条，不够就是说明加载完成了
                isEnd = true;
            }

            productList.addAll(products);
            if(productList.size() == 0){
                //没有
                noProductTv.setVisibility(View.VISIBLE);
                productGv.setVisibility(View.GONE);
            }else{
                //存在
                //没有
                noProductTv.setVisibility(View.GONE);
                productGv.setVisibility(View.VISIBLE);
                setProductAdapter();
            }
        }else{
            isEnd = true;
            if(productList.size() == 0){
                //没有
                noProductTv.setVisibility(View.VISIBLE);
                productGv.setVisibility(View.GONE);
            }else {
                noProductTv.setVisibility(View.GONE);
                productGv.setVisibility(View.VISIBLE);
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
            case R.id.tv_no_product:
                startFindProducts();
                break;
            case R.id.back_product_list:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
    public void onScrollStateChanged(AbsListView absListView, int i) {
        // 当不滚动时
        if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                //加载更多功能的代码
                //监听ListView滑动到底部，加载更多评论
                if(isEnd){  //到底
                    Toast.makeText(ProductListActivity.this,"已经到底了",Toast.LENGTH_SHORT).show();
                }else {
                    startFindProducts();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
