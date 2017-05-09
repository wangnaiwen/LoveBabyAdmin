package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Article;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IUpdateArticle {
    /**
     * 加载数据
     * */
    void updateArticle(Context context, Article article, ArticleUpdateListener articleUpdateListener);

    /**
     * 加载数据完成的回调
     * */
    interface ArticleUpdateListener{
        void complete(boolean isSuccess);
    }
}
