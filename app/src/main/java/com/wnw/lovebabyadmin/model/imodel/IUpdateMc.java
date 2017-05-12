package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Mc;

/**
 * Created by wnw on 2017/5/12.
 */

public interface IUpdateMc {
    /**
     * 加载数据
     * */
    void updateMc(Context context, Mc mc, McUpdateListener updateListener);

    /**
     * 加载数据完成的回调
     * */
    interface McUpdateListener{
        void complete(boolean isSuccess);
    }
}
