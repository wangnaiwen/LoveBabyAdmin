package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;


import com.wnw.lovebabyadmin.domain.Mc;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindMcsModel {
    /**
     * find data
     * */
    void findMcs(Context context, FindMcsListener findMcsListener);

    /**
     * completed
     * */
    interface FindMcsListener{
        void complete(List<Mc> mcs);
    }
}
