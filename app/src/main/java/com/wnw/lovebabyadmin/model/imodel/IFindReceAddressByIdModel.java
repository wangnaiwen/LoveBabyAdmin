package com.wnw.lovebabyadmin.model.imodel;

import android.content.Context;

import com.wnw.lovebabyadmin.domain.ReceAddress;


/**
 * Created by wnw on 2017/4/18.
 */

public interface IFindReceAddressByIdModel {
    /**
     * find data
     * */
    void findReceAddressById(Context context, int id, ReceAddressFindByIdListener receAddressFindByIdListener);

    /**
     * completed
     * */
    interface ReceAddressFindByIdListener{
        void complete(ReceAddress address);
    }
}
