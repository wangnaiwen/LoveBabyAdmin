package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;


import com.wnw.lovebabyadmin.domain.Sc;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindScByMcIdModel {
    /**
     * find data
     * */
    void findScByMcId(Context context, int mcId, FindScByMcIdListener findScByMcIdListener);

    /**
     * completed
     * */
    interface FindScByMcIdListener{
        void complete(List<Sc> scs);
    }
}
