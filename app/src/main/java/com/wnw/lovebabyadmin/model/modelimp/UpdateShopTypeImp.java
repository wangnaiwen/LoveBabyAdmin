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
import com.wnw.lovebabyadmin.model.imodel.IUpdateShopType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/5/8.
 */

public class UpdateShopTypeImp implements IUpdateShopType {
    private Context context;
    private ShopUpdateListener shopUpdateListener;
    private boolean isSuccess;

    @Override
    public void updateShopType(Context context, int id, int type, ShopUpdateListener shopUpdateListener) {
        this.context = context;
        this.shopUpdateListener = shopUpdateListener;
        sendRequestWithVolley(id,type);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(int id, int type){

        String url = NetConfig.SERVICE + NetConfig.UPDATE_SHOP_TYPE;
        url = url + "id=" + id
                +"&type=" + type;
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
            isSuccess = jsonObject.getBoolean("updateShopType");
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
        if(shopUpdateListener != null){
            shopUpdateListener.complete(isSuccess);
        }
    }
}
