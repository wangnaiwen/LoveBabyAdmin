package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.model.imodel.IFindProductByKeyword;
import com.wnw.lovebabyadmin.model.modelimp.FindProductByKeywordImp;
import com.wnw.lovebabyadmin.view.IFindProductByKeywordView;

import java.util.List;

/**
 * Created by wnw on 2017/5/12.
 */

public class FindProductByKeywordPresenter {
    private Context context;
    //view
    private IFindProductByKeywordView findProductByKeywordView;
    //model
    private IFindProductByKeyword findProductByKeyword = new FindProductByKeywordImp();
    //ͨ通过构造函数传入view

    public FindProductByKeywordPresenter(Context context, IFindProductByKeywordView findProductByKeywordView) {
        this.context = context;
        this.findProductByKeywordView = findProductByKeywordView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindProductByKeywordView findProductByKeywordView){
        this.findProductByKeywordView = findProductByKeywordView;
    }

    //加载数据
    public void findProductByKeyword(String keyword) {
        //加载进度条
        findProductByKeywordView.showDialog();
        //model进行数据获取
        if(findProductByKeyword != null){
            findProductByKeyword.findProductByKeyword(context, keyword, new IFindProductByKeyword.ProductFindByKeywordListener() {
                @Override
                public void complete(List<Product> products) {
                    if (findProductByKeywordView != null){
                        findProductByKeywordView.showProducts(products);
                    }
                }
            });
        }
    }
}
