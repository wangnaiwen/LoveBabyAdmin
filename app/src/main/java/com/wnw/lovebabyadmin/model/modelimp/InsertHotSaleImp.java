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
import com.wnw.lovebabyadmin.domain.HotSale;
import com.wnw.lovebabyadmin.model.imodel.IInsertHotSale;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/5/25.
 */

public class InsertHotSaleImp implements IInsertHotSale {
    private Context context;
    private InsertHotSaleListener insertHotSaleListener;
    private boolean isSuccess;

    @Override
    public void insertHotSale(Context context, HotSale hotSale, InsertHotSaleListener insertHotSaleListener) {
        this.context = context;
        this.insertHotSaleListener = insertHotSaleListener;
        sendRequestWithVolley(hotSale);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(HotSale hotSale){

        String time = "";
        try {
            time = URLEncoder.encode(hotSale.getTime(), "UTF-8");
        }catch (Exception e){

        }

        String url = NetConfig.SERVICE + NetConfig.INSERT_HOTSALE;
        url = url + "time=" + time
                +"&productId=" + hotSale.getProductId();
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
            isSuccess = jsonObject.getBoolean("insertHotSale");
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
        if(insertHotSaleListener != null){
            insertHotSaleListener.complete(isSuccess);
        }
    }
}
