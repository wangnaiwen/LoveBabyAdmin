package com.wnw.lovebabyadmin.model.modelimp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wnw.lovebabyadmin.config.NetConfig;
import com.wnw.lovebabyadmin.model.imodel.IAdminLogin;
import com.wnw.lovebabyadmin.util.Md5Encode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/5/8.
 */

public class AdminLoginImp implements IAdminLogin {

    private Context context;
    private AdminLoginListener adminLoginListener;
    private boolean isSuccess;

    @Override
    public void adminLogin(Context context, String name, String password, AdminLoginListener adminLoginListener) {
        this.context = context;
        this.adminLoginListener = adminLoginListener;
        sendRequestWithVolley(name, password);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(String name, String password){
        String url = NetConfig.SERVICE + NetConfig.ADMIN_LOGIN;
        url = url + "name=" + name+"&password="+ Md5Encode.getEd5EncodePassword(password);
        Log.d("url",url );
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                parseNetUserWithJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("wnw", "Error:" + error.getNetworkTimeMs() + error.getMessage());
            }
        });
        queue.add(request);
    }

    private void parseNetUserWithJSON(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            isSuccess = jsonObject.getBoolean("adminLogin");
        }catch (JSONException e){
            e.printStackTrace();
        }
        /**
         * 解析完后返回数据
         * */
        retData();
    }

    /***
     * return data
     */

    private void retData(){
        if(adminLoginListener != null){
            adminLoginListener.complete(isSuccess);
        }
    }
}
