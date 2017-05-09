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
import com.wnw.lovebabyadmin.model.imodel.IFindProductByIdModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/18.
 */

public class FindProductByIdImpl implements IFindProductByIdModel {

    private Context context;
    private ProductFindByIdListener productFindByIdListener;
    private Product returnData;

    @Override
    public void findProductById(Context context, int id, ProductFindByIdListener productFindByIdListener) {
        this.context = context;
        this.productFindByIdListener = productFindByIdListener;
        sendRequestWithVolley(id);
    }


    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int id){
        String url = NetConfig.SERVICE+NetConfig.FIND_PRODUCT_BY_ID
                +"id="+id;
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
            JSONObject resultObject = jsonObject.getJSONObject("findProductById");
            if(resultObject != null){
                returnData = new Product();
                returnData.setId(resultObject.getInt("id"));
                returnData.setScId(resultObject.getInt("scId"));
                returnData.setRetailPrice(resultObject.getInt("retailPrice"));
                returnData.setStandardPrice(resultObject.getInt("standardPrice"));
                returnData.setDescription(resultObject.getString("description"));
                returnData.setName(resultObject.getString("name"));
                returnData.setCoverImg(resultObject.getString("coverImg"));
                returnData.setNumbering(resultObject.getString("numbering"));
                returnData.setBrand(resultObject.getString("brand"));
            }
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
        if(productFindByIdListener != null){
            productFindByIdListener.complete(returnData);
        }
    }
}
