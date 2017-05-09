package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Article;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IInsertArticle {
    /**
     * 加载数据
     * */
    void insertArticle(Context context, Article article, ArticleInsertListener articleInsertListener);

    /**
     * 加载数据完成的回调
     * */
    interface ArticleInsertListener{
        void complete(boolean isSuccess);
    }
}
