package com.wnw.lovebabyadmin.util;

/**
 * Created by wnw on 2017/4/18.
 */

public class TimeConvert {
    public static String getTime(String time){
        if(time.length() == 17){
            return time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "
                    + time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14)
                    +":" + time.substring(14,17);
        }else {
            return time;
        }
    }
}
