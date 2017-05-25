package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.ProductImage;
import com.wnw.lovebabyadmin.model.imodel.IInsertProductImage;
import com.wnw.lovebabyadmin.model.modelimp.InsertProductImageImp;
import com.wnw.lovebabyadmin.view.IInsertProductImageView;


/**
 * Created by wnw on 2017/5/25.
 */

public class InsertProductImagePresenter {
    private Context context;
    //view
    private IInsertProductImageView insertProductImageView;
    //model
    private IInsertProductImage iInsertProductImage = new InsertProductImageImp();
    //ͨ通过构造函数传入view

    public InsertProductImagePresenter(Context context, IInsertProductImageView insertProductImageView) {
        super();
        this.context = context;
        this.insertProductImageView = insertProductImageView;
    }

    public void setView(IInsertProductImageView insertProductImageView){
        this.insertProductImageView = insertProductImageView;
    }

    //加载数据
    public void insertProductImage(ProductImage image) {
        //加载进度条
        insertProductImageView.showDialog();
        //model进行数据获取
        if(iInsertProductImage != null){
            iInsertProductImage.insertProductImage(context, image, new IInsertProductImage.InsertProductImageListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (insertProductImageView != null){
                        insertProductImageView.showInsertProductImageResult(isSuccess);
                    }
                }
            });
        }
    }
}
