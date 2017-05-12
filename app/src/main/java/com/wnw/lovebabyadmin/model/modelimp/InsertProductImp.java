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
import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.model.imodel.IInsertProduct;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/5/12.
 */

public class InsertProductImp implements IInsertProduct{
    private Context context;
    private ProductInsertListener productInsertListener;
    private boolean isSuccess;

    @Override
    public void insertProduct(Context context, Product product, ProductInsertListener productInsertListener) {
        this.context = context;
        this.productInsertListener = productInsertListener;
        sendRequestWithVolley(product);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(Product product){
        String numbering = "";
        String name = "";
        String brand = "";
        String description = "";
        String coverImg = "";
        try {
            numbering = URLEncoder.encode(product.getNumbering(), "UTF-8");
            name = URLEncoder.encode(product.getName(), "UTF-8");
            brand = URLEncoder.encode(product.getBrand(), "UTF-8");
            description = URLEncoder.encode(product.getDescription(), "UTF-8");
            coverImg = URLEncoder.encode(product.getCoverImg(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        String url = NetConfig.SERVICE + NetConfig.INSERT_PRODUCT;
        url = url +"name=" + name
                +"&brand=" + brand
                +"&numbering=" + numbering
                + "&description" + description
                + "&coverImg=" + coverImg
                + "&retailPrice=" + product.getStandardPrice()
                + "&standardPrice=" + product.getStandardPrice()
                + "&scId=" + product.getScId()
                + "&count=" + product.getCount();
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
            isSuccess = jsonObject.getBoolean("insertProduct");
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
        if(productInsertListener != null){
            productInsertListener.complete(isSuccess);
        }
    }
}
