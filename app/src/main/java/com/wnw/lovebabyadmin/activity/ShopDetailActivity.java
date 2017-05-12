package com.wnw.lovebabyadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.domain.Shop;
import com.wnw.lovebabyadmin.presenter.UpdateShopTypePresenter;
import com.wnw.lovebabyadmin.view.IUpdateShopTypeView;

/**
 * Created by wnw on 2017/5/9.
 */

public class ShopDetailActivity extends Activity implements View.OnClickListener, IUpdateShopTypeView{

    private ImageView back;
    private TextView ownerTv;
    private TextView nameTv;
    private TextView idCardTv;
    private TextView userIdTv;
    private TextView inviteeTv;
    private TextView reviewYesTv;
    private TextView reviewNoTv;

    private Shop shop;

    private UpdateShopTypePresenter updateShopTypePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        getShop();
        initView();
        initPresenter();
    }

    private void getShop(){
        Intent intent = getIntent();
        shop = (Shop) intent.getSerializableExtra("shop");
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back);
        ownerTv = (TextView)findViewById(R.id.tv_owner);
        nameTv = (TextView)findViewById(R.id.tv_shop_name);
        idCardTv = (TextView)findViewById(R.id.tv_id_card);
        userIdTv = (TextView)findViewById(R.id.tv_user_id);
        inviteeTv = (TextView)findViewById(R.id.tv_invitee_id);
        reviewYesTv = (TextView)findViewById(R.id.tv_review_yes);
        reviewNoTv = (TextView)findViewById(R.id.tv_review_no);

        back.setOnClickListener(this);
        reviewYesTv.setOnClickListener(this);
        reviewNoTv.setOnClickListener(this);

        ownerTv.setText("拥有者姓名："+shop.getOwner());
        nameTv.setText("店铺名称："+shop.getName());
        idCardTv.setText("身份证号："+shop.getIdCard());
        userIdTv.setText("用户ID："+shop.getUserId());
        inviteeTv.setText("邀请者ID："+shop.getInvitee());
    }

    private void initPresenter(){
        updateShopTypePresenter = new UpdateShopTypePresenter(this,this);
    }

    private void updateShopType(){
        updateShopTypePresenter.updateShopType(shop.getId(), shop.getReviewType());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_review_yes:
                shop.setReviewType(2);  //通过
                updateShopType();
                break;
            case R.id.tv_review_no:
                shop.setReviewType(3);  //不通过
                updateShopType();
                break;
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
    public void showUpdateShopResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(this, "审核成功", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            Toast.makeText(this, "审核失败，请查看网络", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
