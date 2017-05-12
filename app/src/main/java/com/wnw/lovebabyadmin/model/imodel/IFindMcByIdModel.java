package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Mc;


/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindMcByIdModel {
    /**
     * find data
     * */
    void findMcById(Context context, int id, FindMcByIdListener findMcByIdListener);

    /**
     * completed
     * */
    interface FindMcByIdListener{
        void complete(Mc mc);
    }
}
