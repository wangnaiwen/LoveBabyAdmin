package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IAdminLogin {
    /**
     * 加载数据
     * */
    void adminLogin(Context context, String name, String password, AdminLoginListener adminLoginListener);

    /**
     * 加载数据完成的回调
     * */
    interface AdminLoginListener{
        void complete(boolean isSuccess);
    }
}
