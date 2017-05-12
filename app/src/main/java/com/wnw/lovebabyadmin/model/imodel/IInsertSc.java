package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Sc;

/**
 * Created by wnw on 2017/5/12.
 */

public interface IInsertSc {
    /**
     * 加载数据
     * */
    void insertSc(Context context, Sc sc, ScInsertListener scInsertListener);

    /**
     * 加载数据完成的回调
     * */
    interface ScInsertListener{
        void complete(boolean isSuccess);
    }
}
