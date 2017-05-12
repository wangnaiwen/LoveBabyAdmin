package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Mc;
import com.wnw.lovebabyadmin.model.imodel.IUpdateMc;
import com.wnw.lovebabyadmin.model.modelimp.UpdateMcImp;
import com.wnw.lovebabyadmin.view.IUpdateMcView;

/**
 * Created by wnw on 2017/5/12.
 */

public class UpdateMcPresenter {
    private Context context;
    //view
    private IUpdateMcView updateMcView;
    //model
    private IUpdateMc updateMc = new UpdateMcImp();
    //ͨ通过构造函数传入view

    public UpdateMcPresenter(Context context, IUpdateMcView updateMcView) {
        this.context = context;
        this.updateMcView = updateMcView;
    }

    public void setView(IUpdateMcView updateMcView){
        this.updateMcView = updateMcView;
    }

    //加载数据
    public void updateMc(Mc mc) {
        //加载进度条
        updateMcView.showDialog();
        //model进行数据获取
        if(updateMc != null) {
            updateMc.updateMc(context, mc, new IUpdateMc.McUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (updateMcView != null){
                        updateMcView.showUpdateMcResult(isSuccess);
                    }
                }
            });
        }
    }
}
