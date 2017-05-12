package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Mc;
import com.wnw.lovebabyadmin.model.imodel.IFindMcByIdModel;
import com.wnw.lovebabyadmin.model.modelimp.FindMcByIdImpl;
import com.wnw.lovebabyadmin.view.IFindMcByIdView;

/**
 * Created by wnw on 2017/4/20.
 */

public class FindMcByIdPresenter {
    private Context context;
    private IFindMcByIdView findMcByIdView;
    private IFindMcByIdModel findMcByIdModel = new FindMcByIdImpl();

    public FindMcByIdPresenter(Context context, IFindMcByIdView findMcByIdView) {
        this.context = context;
        this.findMcByIdView = findMcByIdView;
    }

    public void View(IFindMcByIdView findMcByIdView){
        this.findMcByIdView = findMcByIdView;
    }

    //加载数据
    public void findMcbyId(int id) {
        //加载进度条
        findMcByIdView.showDialog();
        //model进行数据获取
        if(findMcByIdModel != null) {
            findMcByIdModel.findMcById(context, id, new IFindMcByIdModel.FindMcByIdListener() {
                @Override
                public void complete(Mc mc) {
                    if(findMcByIdView != null){
                        findMcByIdView.showMc(mc);
                    }
                }
            });
        }
    }
}
