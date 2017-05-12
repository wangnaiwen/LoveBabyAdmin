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
import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.model.imodel.IFindScByMcIdModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public class FindScByMcIdImpl implements IFindScByMcIdModel {

    private Context context;
    private FindScByMcIdListener findScByMcIdListener;
    private List<Sc> scList;

    @Override
    public void findScByMcId(Context context, int mcId, FindScByMcIdListener findScByMcIdListener) {
        this.context = context;
        this.findScByMcIdListener = findScByMcIdListener;
        sendRequestWithVolley(mcId);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int mcId){
        String url = NetConfig.SERVICE+NetConfig.FIND_SC_BY_MC_ID
                +"mcId="+mcId;

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
            JSONArray jsonArray = jsonObject.getJSONArray("findScByMcId");
            if(jsonArray != null){
                scList = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = length-1 ; i >= 0 ; i--){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Sc sc = new Sc();
                    sc.setId(object.getInt("id"));
                    sc.setMcId(object.getInt("mcId"));
                    sc.setName(object.getString("name"));
                    sc.setImage(object.getString("image"));
                    scList.add(sc);
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
        if(findScByMcIdListener != null){
            findScByMcIdListener.complete(scList);
        }
    }

}
