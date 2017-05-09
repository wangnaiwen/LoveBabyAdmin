package com.wnw.lovebabyadmin.util;

/**
 * Created by wnw on 2017/4/17.
 */

public class OrderTypeConvert {

    public static String getStringType(int type){
        String stringType = "";

        if(type == 0){
            stringType = "无效订单";
        }else if(type == 1){
            stringType = "待支付";
        }else if(type == 2){
            stringType = "待发货";
        }else if(type == 3){
            stringType = "待收货";
        }else if(type == 4){
            stringType = "待评价";
        }
        else if(type == 5){
            stringType = "已完成";
        }

        return stringType;
    }
}
