package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Sc;

/**
 * Created by wnw on 2017/5/12.
 */

public interface IUpdateSc {
    /**
     * 加载数据
     * */
    void updateSc(Context context, Sc sc, ScUpdateListener updateListener);

    /**
     * 加载数据完成的回调
     * */
    interface ScUpdateListener{
        void complete(boolean isSuccess);
    }
}
