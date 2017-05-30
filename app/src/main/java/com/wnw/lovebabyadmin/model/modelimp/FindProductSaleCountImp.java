package com.wnw.lovebabyadmin.model.modelimp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wnw.lovebabyadmin.config.NetConfig;
import com.wnw.lovebabyadmin.domain.ProductSaleCount;
import com.wnw.lovebabyadmin.model.imodel.IFindProductSaleCountImp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/28.
 */

public class FindProductSaleCountImp implements IFindProductSaleCountImp {
    private Context context;
    private FindProductSaleCountListener findProductSaleCountListener;
    private List<ProductSaleCount> returnData;

    @Override
    public void findProductSaleCount(Context context, FindProductSaleCountListener findProductSaleCountListener) {
        this.context = context;
        this.findProductSaleCountListener = findProductSaleCountListener;
        sendRequestWithVolley();
    }

    private void sendRequestWithVolley(){
        String url = NetConfig.SERVICE + NetConfig.FIND_PRODUCT_SALE_COUNT;
        Log.d("url", url);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
            JSONArray jsonArray = jsonObject.getJSONArray("findProductSaleCount");
            if(jsonArray != null){
                returnData = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    ProductSaleCount saleCount = new ProductSaleCount();
                    saleCount.setName(object.getString("name"));
                    saleCount.setCount(object.getInt("count"));
                    returnData.add(saleCount);
                }
            }else {
                Toast.makeText(context, "找不到", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(findProductSaleCountListener != null){
            findProductSaleCountListener.complete(returnData);
        }
    }

}
