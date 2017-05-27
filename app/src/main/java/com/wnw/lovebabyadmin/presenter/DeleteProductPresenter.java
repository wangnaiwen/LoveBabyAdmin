package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.model.imodel.IDeleteProduct;
import com.wnw.lovebabyadmin.model.modelimp.DeleteProductImp;
import com.wnw.lovebabyadmin.view.IDeleteProductView;

/**
 * Created by wnw on 2017/5/25.
 */

public class DeleteProductPresenter {
    private Context context;
    //view
    private IDeleteProductView deleteProductView;
    //model
    private IDeleteProduct deleteProduct = new DeleteProductImp();
    //ͨ通过构造函数传入view

    public DeleteProductPresenter(Context context, IDeleteProductView deleteProductView) {
        this.context = context;
        this.deleteProductView = deleteProductView;
    }

    public void setView(IDeleteProductView deleteProductView) {
        this.deleteProductView = deleteProductView;
    }

    //加载数据
    public void deleteProduct(int id) {
        //加载进度条
        deleteProductView.showDialog();
        //model进行数据获取
        if (deleteProduct != null) {
            deleteProduct.deleteProduct(context, id, new IDeleteProduct.ProductDeleteListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (deleteProductView != null){
                        deleteProductView.showDeleteProductResult(isSuccess);
                    }
                }
            });
        }
    }
}
