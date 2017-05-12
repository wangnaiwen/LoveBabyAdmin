package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.model.imodel.IInsertSc;
import com.wnw.lovebabyadmin.model.modelimp.InsertScImp;
import com.wnw.lovebabyadmin.view.IInsertScView;

/**
 * Created by wnw on 2017/5/12.
 */

public class InsertScPresenter {
    private Context context;
    //view
    private IInsertScView insertScView;
    //model
    private IInsertSc insertSc = new InsertScImp();
    //ͨ通过构造函数传入view

    public InsertScPresenter(Context context, IInsertScView insertScView) {
        this.context = context;
        this.insertScView = insertScView;
    }

    public void setView(IInsertScView insertScView){
        this.insertScView = insertScView;
    }

    //加载数据
    public void insertSc(Sc sc) {
        //加载进度条
        insertScView.showDialog();
        //model进行数据获取
        if(insertSc != null) {
            insertSc.insertSc(context, sc, new IInsertSc.ScInsertListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (insertScView != null){
                        insertScView.showInsertScResult(isSuccess);
                    }
                }
            });
        }
    }
}
