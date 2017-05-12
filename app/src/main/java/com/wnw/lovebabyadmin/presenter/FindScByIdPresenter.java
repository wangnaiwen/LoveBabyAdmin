package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.model.imodel.IFindScByIdModel;
import com.wnw.lovebabyadmin.model.modelimp.FindScByIdImpl;
import com.wnw.lovebabyadmin.view.IFindScByIdView;


/**
 * Created by wnw on 2017/4/20.
 */

public class FindScByIdPresenter {
    private Context context;
    private IFindScByIdView findScByIdView;
    private IFindScByIdModel findScByIdModel = new FindScByIdImpl();

    public FindScByIdPresenter(Context context, IFindScByIdView findScByIdView) {
        this.context = context;
        this.findScByIdView = findScByIdView;
    }

    public void View(IFindScByIdView findScByIdView){
        this.findScByIdView = findScByIdView;
    }

    //加载数据
    public void findScbyId(int id) {
        //加载进度条
        findScByIdView.showDialog();
        //model进行数据获取
        if(findScByIdModel != null) {
            findScByIdModel.findScById(context, id, new IFindScByIdModel.FindScByIdListener() {
                @Override
                public void complete(Sc sc) {
                    if(findScByIdView != null){
                        findScByIdView.showSc(sc);
                    }
                }
            });
        }
    }
}
