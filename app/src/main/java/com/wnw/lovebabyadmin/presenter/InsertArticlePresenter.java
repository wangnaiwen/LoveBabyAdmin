package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Article;
import com.wnw.lovebabyadmin.model.imodel.IInsertArticle;
import com.wnw.lovebabyadmin.model.modelimp.InsertArticleImp;
import com.wnw.lovebabyadmin.view.IInsertArticleView;

/**
 * Created by wnw on 2017/5/8.
 */

public class InsertArticlePresenter {
    private Context context;
    //view
    private IInsertArticleView insertArticleView;
    //model
    private IInsertArticle insertArticle = new InsertArticleImp();
    //ͨ通过构造函数传入view

    public InsertArticlePresenter(Context context, IInsertArticleView insertArticleView) {
        this.context = context;
        this.insertArticleView = insertArticleView;
    }

    public void setView(IInsertArticleView insertArticleView){
        this.insertArticleView = insertArticleView;
    }

    //加载数据
    public void insertArticle(Article article) {
        //加载进度条
        insertArticleView.showDialog();
        //model进行数据获取
        if(insertArticle != null) {
            insertArticle.insertArticle(context, article, new IInsertArticle.ArticleInsertListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (insertArticleView != null){
                        insertArticleView.showInsertArticleResult(isSuccess);
                    }
                }
            });
        }
    }
}
