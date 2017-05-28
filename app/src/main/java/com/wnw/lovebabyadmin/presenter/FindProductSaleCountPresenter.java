package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.ProductSaleCount;
import com.wnw.lovebabyadmin.model.imodel.IFindProductSaleCountImp;
import com.wnw.lovebabyadmin.model.modelimp.FindProductSaleCountImp;
import com.wnw.lovebabyadmin.view.IFindProductSaleCountView;

import java.util.List;

/**
 * Created by wnw on 2017/5/28.
 */

public class FindProductSaleCountPresenter {
    private Context context;
    private IFindProductSaleCountView findProductSaleCountView;
    private IFindProductSaleCountImp findProductSaleCount = new FindProductSaleCountImp();

    public FindProductSaleCountPresenter(Context context,IFindProductSaleCountView findProductSaleCountView){
        this.context = context;
        this.findProductSaleCountView = findProductSaleCountView;
    }

    public void setView(IFindProductSaleCountView findProductSaleCountView){
        this.findProductSaleCountView = findProductSaleCountView;
    }
    //加载数据
    public void findProductSaleCount() {
        //加载进度条
        findProductSaleCountView.showDialog();
        //model进行数据获取
        if(findProductSaleCount != null){
            findProductSaleCount.findProductSaleCount(context, new IFindProductSaleCountImp.FindProductSaleCountListener() {
                @Override
                public void complete(List<ProductSaleCount> productSaleCounts) {
                    findProductSaleCountView.showProductSaleCount(productSaleCounts);
                }
            });
        }
    }

}
