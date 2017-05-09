package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.model.imodel.IDeleteArticle;
import com.wnw.lovebabyadmin.model.modelimp.DeleteArticleImp;
import com.wnw.lovebabyadmin.view.IDeleteArticleView;
/**
 * Created by wnw on 2017/5/8.
 */

public class DeleteArticlePresenter {
    private Context context;
    //view
    private IDeleteArticleView deleteArticleView;
    //model
    private IDeleteArticle deleteArticle = new DeleteArticleImp();
    //ͨ通过构造函数传入view

    public DeleteArticlePresenter(Context context, IDeleteArticleView deleteArticleView) {
        this.context = context;
        this.deleteArticleView = deleteArticleView;
    }

    public void setView(IDeleteArticleView deleteArticleView) {
        this.deleteArticleView = deleteArticleView;
    }

    //加载数据
    public void setDeleteArticle(int id) {
        //加载进度条
        deleteArticleView.showDialog();
        //model进行数据获取
        if (deleteArticle != null) {
            deleteArticle.deleteArticle(context, id, new IDeleteArticle.ArticleDeleteListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (deleteArticleView != null){
                        deleteArticleView.showDeleteResult(isSuccess);
                    }
                }
            });
        }
    }
}
