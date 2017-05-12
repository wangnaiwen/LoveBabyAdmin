package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Mc;
import com.wnw.lovebabyadmin.model.imodel.IInsertMc;
import com.wnw.lovebabyadmin.model.modelimp.InsertMcImp;
import com.wnw.lovebabyadmin.view.IInsertMcView;

/**
 * Created by wnw on 2017/5/12.
 */

public class InsertMcPresenter {
    private Context context;
    //view
    private IInsertMcView insertMcView;
    //model
    private IInsertMc insertMc = new InsertMcImp();
    //ͨ通过构造函数传入view

    public InsertMcPresenter(Context context, IInsertMcView insertMcView) {
        this.context = context;
        this.insertMcView = insertMcView;
    }

    public void setView(IInsertMcView insertMcView){
        this.insertMcView = insertMcView;
    }

    //加载数据
    public void insertMc(Mc mc) {
        //加载进度条
        insertMcView.showDialog();
        //model进行数据获取
        if(insertMc != null) {
            insertMc.insertMc(context, mc, new IInsertMc.McInsertListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (insertMcView != null){
                        insertMcView.showInsertMcResult(isSuccess);
                    }
                }
            });
        }
    }
}
