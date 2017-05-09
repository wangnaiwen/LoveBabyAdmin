package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Article;
import com.wnw.lovebabyadmin.model.imodel.IUpdateArticle;
import com.wnw.lovebabyadmin.model.modelimp.UpdateArticleImp;
import com.wnw.lovebabyadmin.view.IUpdateArticleView;

/**
 * Created by wnw on 2017/5/8.
 */

public class UpdateArticlePresenter {
    private Context context;
    //view
    private IUpdateArticleView updateArticleView;
    //model
    private IUpdateArticle updateArticle = new UpdateArticleImp();
    //ͨ通过构造函数传入view

    public UpdateArticlePresenter(Context context, IUpdateArticleView updateArticleView) {
        this.context = context;
        this.updateArticleView = updateArticleView;
    }

    public void setView(IUpdateArticleView updateArticleView){
        this.updateArticleView = updateArticleView;
    }

    //加载数据
    public void updateArticle(Article article) {
        //加载进度条
        updateArticleView.showDialog();
        //model进行数据获取
        if(updateArticle != null) {
            updateArticle.updateArticle(context, article, new IUpdateArticle.ArticleUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (updateArticleView != null){
                        updateArticleView.showUpdateArticleResult(isSuccess);
                    }
                }
            });
        }
    }
}
