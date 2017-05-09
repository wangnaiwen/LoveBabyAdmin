package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IDeleteArticle {
    /**
     * 加载数据
     * */
    void deleteArticle(Context context, int id, ArticleDeleteListener articleDeleteListener);

    /**
     * 加载数据完成的回调
     * */
    interface ArticleDeleteListener{
        void complete(boolean isSuccess);
    }
}
