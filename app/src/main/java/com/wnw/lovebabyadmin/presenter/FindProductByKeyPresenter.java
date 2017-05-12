package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.model.imodel.IFindProductByKeyModel;
import com.wnw.lovebabyadmin.model.modelimp.FindProductByKeyImpl;
import com.wnw.lovebabyadmin.view.IFindProductByKeyView;

import java.util.List;

/**
 * Created by wnw on 2017/4/26.
 */

public class FindProductByKeyPresenter {
    private Context context;
    //view
    private IFindProductByKeyView findProductByKeyView;
    //model
    private IFindProductByKeyModel findProductByKeyModel = new FindProductByKeyImpl();
    //ͨ通过构造函数传入view

    public FindProductByKeyPresenter(Context context, IFindProductByKeyView findProductByKeyView) {
        this.context = context;
        this.findProductByKeyView = findProductByKeyView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindProductByKeyView findProductByKeyView){
        this.findProductByKeyView = findProductByKeyView;
    }

    //加载数据
    public void findProductByKey(String key, int userId) {
        //加载进度条
        findProductByKeyView.showDialog();
        //model进行数据获取
        if(findProductByKeyModel != null){
            findProductByKeyModel.findProductByKey(context, key, userId, new IFindProductByKeyModel.FindProductByKeyListener() {
                @Override
                public void complete(List<Product> products) {
                    if (findProductByKeyView != null){
                        findProductByKeyView.showProductByUserId(products);
                    }
                }
            });
        }
    }
}
