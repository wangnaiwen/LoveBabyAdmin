package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.model.imodel.IUpdateSc;
import com.wnw.lovebabyadmin.model.modelimp.UpdateScImp;
import com.wnw.lovebabyadmin.view.IUpdateScView;

/**
 * Created by wnw on 2017/5/12.
 */

public class UpdateScPresenter {
    private Context context;
    //view
    private IUpdateScView updateScView;
    //model
    private IUpdateSc updateSc = new UpdateScImp();
    //ͨ通过构造函数传入view

    public UpdateScPresenter(Context context,IUpdateScView updateScView) {
        this.context = context;
        this.updateScView = updateScView;
    }

    public void setView(IUpdateScView updateScView){
        this.updateScView = updateScView;
    }

    //加载数据
    public void updateSc(Sc sc) {
        //加载进度条
        updateScView.showDialog();
        //model进行数据获取
        if(updateSc != null) {
            updateSc.updateSc(context, sc, new IUpdateSc.ScUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (updateScView != null){
                        updateScView.showUpdateScResult(isSuccess);
                    }
                }
            });
        }
    }
}
