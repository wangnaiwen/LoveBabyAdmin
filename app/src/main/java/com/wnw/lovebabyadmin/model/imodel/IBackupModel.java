package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

/**
 * Created by wnw on 2017/6/4.
 */

public interface IBackupModel {
    /**
     * delete data
     * */
    void backup(Context context, BackupListener backupListener);

    /**
     * completed
     * */
    interface BackupListener{
        void complete(boolean isSuccess);
    }
}
