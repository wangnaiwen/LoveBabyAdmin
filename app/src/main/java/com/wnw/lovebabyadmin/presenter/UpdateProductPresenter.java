package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.model.imodel.IUpdateProduct;
import com.wnw.lovebabyadmin.model.modelimp.UpdateProductImp;
import com.wnw.lovebabyadmin.view.IUpdateProductView;

/**
 * Created by wnw on 2017/5/12.
 */

public class UpdateProductPresenter {
    private Context context;
    //view
    private IUpdateProductView iUpdateProductView;
    //model
    private IUpdateProduct updateProduct = new UpdateProductImp();
    //ͨ通过构造函数传入view

    public UpdateProductPresenter(Context context, IUpdateProductView iUpdateProductView) {
        this.context = context;
        this.iUpdateProductView = iUpdateProductView;
    }

    public void setView(IUpdateProductView iUpdateProductView){
        this.iUpdateProductView = iUpdateProductView;
    }

    //加载数据
    public void updateProduct(Product product) {
        //加载进度条
        iUpdateProductView.showDialog();
        //model进行数据获取
        if(updateProduct != null) {
            updateProduct.updateProduct(context, product, new IUpdateProduct.ProductUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (iUpdateProductView != null){
                        iUpdateProductView.showUpdateProductResult(isSuccess);
                    }
                }
            });
        }
    }
}
