package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.Sc;


/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindScByIdModel {
    /**
     * find data
     * */
    void findScById(Context context, int id, FindScByIdListener findScByIdListener);

    /**
     * completed
     * */
    interface FindScByIdListener{
        void complete(Sc sc);
    }
}
