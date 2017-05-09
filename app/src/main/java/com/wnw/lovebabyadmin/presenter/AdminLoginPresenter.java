package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.model.imodel.IAdminLogin;
import com.wnw.lovebabyadmin.model.modelimp.AdminLoginImp;
import com.wnw.lovebabyadmin.view.IAdminLoginView;
/**
 * Created by wnw on 2017/5/8.
 */

public class AdminLoginPresenter {
    private Context context;
    //view
    private IAdminLoginView adminLoginView;
    //model
    private IAdminLogin adminLogin = new AdminLoginImp();
    //ͨ通过构造函数传入view

    public AdminLoginPresenter(Context context, IAdminLoginView adminLoginView) {
        this.context = context;
        this.adminLoginView = adminLoginView;
    }

    public void setView(IAdminLoginView adminLoginView) {
        this.adminLoginView = adminLoginView;
    }

    //加载数据
    public void login(String name, String password) {
        //加载进度条
        adminLoginView.showDialog();
        //model进行数据获取
        if (adminLogin != null) {
            adminLogin.adminLogin(context, name, password, new IAdminLogin.AdminLoginListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(adminLoginView != null){
                        adminLoginView.showLoginResult(isSuccess);
                    }
                }
            });
        }
    }
}
