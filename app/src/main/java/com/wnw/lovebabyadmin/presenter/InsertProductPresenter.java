package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.model.imodel.IInsertProduct;
import com.wnw.lovebabyadmin.model.modelimp.InsertProductImp;
import com.wnw.lovebabyadmin.view.IInsertProductView;

/**
 * Created by wnw on 2017/5/12.
 */

public class InsertProductPresenter {
    private Context context;
    //view
    private IInsertProductView insertProductView;
    //model
    private IInsertProduct insertProduct = new InsertProductImp();
    //ͨ通过构造函数传入view

    public InsertProductPresenter(Context context, IInsertProductView insertProductView) {
        this.context = context;
        this.insertProductView = insertProductView;
    }

    public void setView(IInsertProductView insertProductView){
        this.insertProductView = insertProductView;
    }

    //加载数据
    public void insertProduct(Product product) {
        //加载进度条
        insertProductView.showDialog();
        //model进行数据获取
        if(insertProduct != null) {
            insertProduct.insertProduct(context, product, new IInsertProduct.ProductInsertListener() {
                @Override
                public void complete(int id) {
                    if (insertProductView != null){
                        insertProductView.showInsertProductResult(id);
                    }
                }
            });
        }
    }
}
