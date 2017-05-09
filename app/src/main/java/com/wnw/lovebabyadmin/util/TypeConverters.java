package com.wnw.lovebabyadmin.util;

/**
 * Created by wnw on 2016/12/10.
 */

public class TypeConverters {
    /**
     * int convert to float, to string
     */
    public static String IntConvertToString(int num){
        String str = "￥";
        int integerPart = num / 100;
        int decimalsPart = num % 100;
        if(decimalsPart < 10){
            str = str + integerPart + ".0" + decimalsPart;
        }else {
            str = str + integerPart + "." + decimalsPart;
        }

        return str;
    }
    public static String LongConvertToString(long num){
        String str = "￥";
        long integerPart = num / 100;
        long decimalsPart = num % 100;
        if(decimalsPart < 10){
            str = str + integerPart + ".0" + decimalsPart;
        }else {
            str = str + integerPart + "." + decimalsPart;
        }

        return str;
    }
}
