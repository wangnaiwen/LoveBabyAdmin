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
import com.wnw.lovebabyadmin.domain.SpecialPrice;
import com.wnw.lovebabyadmin.model.imodel.IInsertSpecialPrice;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/5/25.
 */

public class InsertSpecialPriceImp implements IInsertSpecialPrice {
    private Context context;
    private InsertSpecialPriceListener insertSpecialPriceListener;
    private boolean isSuccess;

    @Override
    public void insertSpecialPrice(Context context, SpecialPrice specialPrice, InsertSpecialPriceListener insertSpecialPriceListener) {
        this.context = context;
        this.insertSpecialPriceListener =  insertSpecialPriceListener;
        sendRequestWithVolley(specialPrice);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(SpecialPrice specialPrice){

        String time = "";
        try {
            time = URLEncoder.encode(specialPrice.getTime(), "UTF-8");
        }catch (Exception e){

        }

        String url = NetConfig.SERVICE + NetConfig.INSERT_SPECIAL_PRICE;
        url = url + "time=" + time
                +"&productId=" + specialPrice.getProductId();
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
            isSuccess = jsonObject.getBoolean("insertSpecialPrice");
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
        if(insertSpecialPriceListener != null){
            insertSpecialPriceListener.complete(isSuccess);
        }
    }
}
