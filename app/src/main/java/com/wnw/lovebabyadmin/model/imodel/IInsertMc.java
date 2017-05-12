package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Mc;

/**
 * Created by wnw on 2017/5/12.
 */

public interface IInsertMc {
    /**
     * 加载数据
     * */
    void insertMc(Context context, Mc mc, McInsertListener mcInsertListener);

    /**
     * 加载数据完成的回调
     * */
    interface McInsertListener{
        void complete(boolean isSuccess);
    }
}
