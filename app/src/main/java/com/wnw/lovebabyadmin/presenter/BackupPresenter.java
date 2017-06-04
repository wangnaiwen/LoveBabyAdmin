package com.wnw.lovebabyadmin.presenter;

import android.content.Context;

import com.wnw.lovebabyadmin.model.imodel.IBackupModel;
import com.wnw.lovebabyadmin.model.modelimp.BackupImp;
import com.wnw.lovebabyadmin.view.IBackupView;

/**
 * Created by wnw on 2017/6/4.
 */

public class BackupPresenter {
    private Context context;
    //view
    private IBackupView backupView;
    //model
    private IBackupModel backupModel = new BackupImp();
    //ͨ通过构造函数传入view

    public BackupPresenter(Context context,IBackupView backupView) {
        this.context = context;
        this.backupView = backupView;
    }

    public void setView(IBackupView backupView){
        this.backupView = backupView;
    }

    //加载数据
    public void backup() {
        //加载进度条
        backupView.showDialog();
        //model进行数据获取
        if(backupModel != null) {
            backupModel.backup(context, new IBackupModel.BackupListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (backupView != null){
                        backupView.showBackupResult(isSuccess);
                    }
                }
            });
        }
    }
}
