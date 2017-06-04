package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.model.imodel.IRecover;
import com.wnw.lovebabyadmin.model.modelimp.RecoverImp;
import com.wnw.lovebabyadmin.view.IRecoverView;

/**
 * Created by wnw on 2017/6/4.
 */

public class RecoverPresenter {
    private Context context;
    //view
    private IRecoverView recoverView;
    //model
    private IRecover recover = new RecoverImp();
    //ͨ通过构造函数传入view

    public RecoverPresenter(Context context,IRecoverView recoverView) {
        this.context = context;
        this.recoverView = recoverView;
    }

    public void setView(IRecoverView recoverView){
        this.recoverView = recoverView;
    }

    //加载数据
    public void recover() {
        //加载进度条
        recoverView.showDialog();
        //model进行数据获取
        if(recover != null) {
            recover.recover(context, new IRecover.RecoverListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (recoverView != null){
                        recoverView.showRecoverResult(isSuccess);
                    }
                }
            });
        }
    }
}
