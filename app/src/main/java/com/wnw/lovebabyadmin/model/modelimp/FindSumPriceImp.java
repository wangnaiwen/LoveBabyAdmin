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
import com.wnw.lovebabyadmin.model.imodel.IFindSumPrice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wnw on 2017/5/27.
 */

public class FindSumPriceImp implements IFindSumPrice{
    private Context context;
    private FindSumPriceListener findSumPriceListener;
    private ArrayList<Integer> days =new ArrayList<>();
    private ArrayList<Integer> months = new ArrayList<>();

    @Override
    public void findSumPrice(Context context, FindSumPriceListener findSumPriceListener) {
        this.context = context;
        this.findSumPriceListener = findSumPriceListener;
        sendRequestWithVolley();
    }

    private void sendRequestWithVolley(){
        String url = NetConfig.SERVICE + NetConfig.FIND_SUM_PRICE;
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
            JSONArray jsonArray = jsonObject.getJSONArray("days");
            int length = jsonArray.length();
            for (int i= 0; i < length; i++){
                days.add(jsonArray.getInt(i));
            }

            JSONArray jsonArray1 = jsonObject.getJSONArray("months");
            int length1 = jsonArray1.length();
            for (int i= 0; i < length1; i++){
                months.add(jsonArray1.getInt(i));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(findSumPriceListener != null){
            findSumPriceListener.complete(days, months);
        }
    }
}
