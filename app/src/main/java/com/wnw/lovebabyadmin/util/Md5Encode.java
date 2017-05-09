package com.wnw.lovebabyadmin.util;

import java.security.MessageDigest;

/**
 * Created by wnw on 2017/4/26.
 */

public class Md5Encode {

    //md5加密
    public static String getEd5EncodePassword(String password){

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        try{

            byte[] byteArray = password.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
