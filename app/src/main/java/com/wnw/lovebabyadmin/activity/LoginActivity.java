package com.wnw.lovebabyadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.domain.Admin;
import com.wnw.lovebabyadmin.presenter.AdminLoginPresenter;
import com.wnw.lovebabyadmin.util.ActivityCollector;
import com.wnw.lovebabyadmin.view.IAdminLoginView;


/**
 * Created by wnw on 2017/5/8.
 */

public class LoginActivity extends Activity implements
        View.OnClickListener, IAdminLoginView{

    private EditText username;      //用户名输入框
    private EditText password;   //密码输入框
    private Button login;        //登录按钮
    private Admin admin;         //管理员
    private AdminLoginPresenter adminLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //如果已经登录过，并且没有退出账号，默认登录，直接跳转到MainActivity
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        if(sharedPreferences != null){
            String id = sharedPreferences.getString("name", "");
            if(!id.equals("")){  //说明已经登录
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
        initView();
        initPresenter();
        ActivityCollector.addActivity(this);
    }

    //初始化View
    private void initView(){
        username = (EditText)findViewById(R.id.store_login_username);
        password = (EditText)findViewById(R.id.store_login_password);
        login = (Button)findViewById(R.id.btn_store_login);
        login.setOnClickListener(this);
    }

    private void initPresenter(){
        adminLoginPresenter = new AdminLoginPresenter(this,this);
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
    public void showLoginResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            openMainAty();
        }else {
            Toast.makeText(this, "手机和密码错误",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_store_login:
                if(validateEditText()){
                    Toast.makeText(this, "手机和密码都不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "正在拼命登录中...",Toast.LENGTH_SHORT).show();
                    //验证密码
                    admin = new Admin();
                    admin.setName(username.getText().toString().trim());
                    admin.setPassword(password.getText().toString().trim());
                    //开始获得数据
                    adminLoginPresenter.login(admin.getName(), admin.getPassword());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 验证两个EditText是否都已经不为空了
     * */
    private boolean validateEditText(){
        return username.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty();
    }

    //登录成功后，打开MainActivity
    private void openMainAty(){
        saveAccount();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("admin",admin);
        startActivity(intent);
        ActivityCollector.finishAllActivity();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    //登录成功，保存登录账号信息到本地
    private void saveAccount(){
        SharedPreferences.Editor editor = getSharedPreferences("account",
                MODE_PRIVATE).edit();
        editor.clear();
        editor.putString("name", admin.getName());
        editor.apply();
    }
}
