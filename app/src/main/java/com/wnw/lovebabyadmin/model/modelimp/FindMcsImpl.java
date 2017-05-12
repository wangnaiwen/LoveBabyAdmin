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
import com.wnw.lovebabyadmin.domain.Mc;
import com.wnw.lovebabyadmin.model.imodel.IFindMcsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public class FindMcsImpl implements IFindMcsModel {

    private Context context;
    private FindMcsListener findMcsListener;
    private List<Mc> mcList;

    @Override
    public void findMcs(Context context, FindMcsListener findMcsListener) {
        this.context = context;
        this.findMcsListener = findMcsListener;
        sendRequestWithVolley();
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(){
        String url = NetConfig.SERVICE+NetConfig.FIND_MCS;

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
            JSONArray jsonArray = jsonObject.getJSONArray("findMcs");
            if(jsonArray != null){
                mcList = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length ; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Mc mc = new Mc();
                    mc.setId(object.getInt("id"));
                    mc.setName(object.getString("name"));
                    mcList.add(mc);
                }
            }else {
                //Toast.makeText(context, "找不到收货地址", Toast.LENGTH_SHORT).show();
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
        if(findMcsListener != null){
            findMcsListener.complete(mcList);
        }
    }
}
