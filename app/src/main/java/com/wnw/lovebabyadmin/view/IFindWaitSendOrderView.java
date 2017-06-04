package com.wnw.lovebabyadmin.view;

import com.wnw.lovebabyadmin.domain.Order;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IFindWaitSendOrderView {
    void showDialog();
    void showOrders(List<Order> orders, List<String> names);
}
