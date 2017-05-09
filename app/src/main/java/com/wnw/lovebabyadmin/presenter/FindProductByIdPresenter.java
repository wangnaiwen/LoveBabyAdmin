package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.model.imodel.IFindProductByIdModel;
import com.wnw.lovebabyadmin.model.modelimp.FindProductByIdImpl;
import com.wnw.lovebabyadmin.view.IFindProductByIdView;


/**
 * Created by wnw on 2017/4/18.
 */

public class FindProductByIdPresenter {

    private Context context;
    //view
    private IFindProductByIdView findProductByIdView;
    //model
    private IFindProductByIdModel findProductByIdModel = new FindProductByIdImpl();
    //ͨ通过构造函数传入view

    public FindProductByIdPresenter(Context context, IFindProductByIdView findProductByIdView) {
        this.context = context;
        this.findProductByIdView = findProductByIdView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindProductByIdView findProductByIdView){
        this.findProductByIdView = findProductByIdView;
    }

    //加载数据
    public void findProductById(int id) {
        //加载进度条
        findProductByIdView.showDialog();
        //model进行数据获取
        if(findProductByIdModel != null){
            findProductByIdModel.findProductById(context, id, new IFindProductByIdModel.ProductFindByIdListener() {
                @Override
                public void complete(Product product) {
                    if (findProductByIdView != null){
                        findProductByIdView.showProduct(product);
                    }
                }
            });
        }
    }
}
