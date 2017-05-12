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
import com.wnw.lovebabyadmin.model.imodel.IUpdateMc;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/5/12.
 */

public class UpdateMcImp implements IUpdateMc{

    private Context context;
    private McUpdateListener updateListener;
    private boolean isSuccess;

    @Override
    public void updateMc(Context context, Mc mc, McUpdateListener updateListener) {
        this.context = context;
        this.updateListener = updateListener;
        sendRequestWithVolley(mc);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(Mc mc){

        String name = "";
        try {
            name = URLEncoder.encode(mc.getName(), "UTF-8");
        }catch (Exception e){

        }

        String url = NetConfig.SERVICE + NetConfig.UPDATE_MC;
        url = url + "name=" + name;
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
            isSuccess = jsonObject.getBoolean("updateMc");
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
        if(updateListener != null){
            updateListener.complete(isSuccess);
        }
    }
}
