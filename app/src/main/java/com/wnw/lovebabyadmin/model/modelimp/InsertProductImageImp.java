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
import com.wnw.lovebabyadmin.domain.ProductImage;
import com.wnw.lovebabyadmin.model.imodel.IInsertProductImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/5/25.
 */

public class InsertProductImageImp implements IInsertProductImage {
    private Context context;
    private InsertProductImageListener insertProductImageListener;
    private boolean isSuccess;

    @Override
    public void insertProductImage(Context context, ProductImage image, InsertProductImageListener insertProductImageListener) {
        this.context = context;
        this.insertProductImageListener = insertProductImageListener;
        sendRequestWithVolley(image);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(ProductImage image){
        String path = "";
        try {
            path = URLEncoder.encode(image.getPath(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        String url = NetConfig.SERVICE + NetConfig.INSERT_PRODUCT_IMAGE;
        url = url +"id=" + image.getId()
                +"&productId=" + image.getProductId()
                +"&path=" + path;
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
            isSuccess = jsonObject.getBoolean("insertProductImage");
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
        if(insertProductImageListener != null){
            insertProductImageListener.complete(isSuccess);
        }
    }
}
