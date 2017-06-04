package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

/**
 * Created by wnw on 2017/6/4.
 */

public interface IRecover {
    /**
     * delete data
     * */
    void recover(Context context, RecoverListener recoverListener);

    /**
     * completed
     * */
    interface RecoverListener{
        void complete(boolean isSuccess);
    }
}
