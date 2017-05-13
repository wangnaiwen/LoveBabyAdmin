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
import com.wnw.lovebabyadmin.domain.Article;
import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.model.imodel.IInsertArticle;
import com.wnw.lovebabyadmin.model.imodel.IInsertSc;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/5/12.
 */

public class InsertScImp implements IInsertSc{
    private Context context;
    private ScInsertListener scInsertListener;
    private boolean isSuccess;

    @Override
    public void insertSc(Context context, Sc sc, ScInsertListener scInsertListener) {
        this.context = context;
        this.scInsertListener = scInsertListener;
        sendRequestWithVolley(sc);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(Sc sc){

        String name = "";
        String image = "";
        try {
            name = URLEncoder.encode(sc.getName(), "UTF-8");
            image = URLEncoder.encode(sc.getImage(), "UTF-8");
        }catch (Exception e){

        }

        String url = NetConfig.SERVICE + NetConfig.INSERT_SC;
        url = url + "name=" + name
                +"&image=" + image
                +"&mcId=" + sc.getMcId();
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
            isSuccess = jsonObject.getBoolean("insertSc");
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
        if(scInsertListener != null){
            scInsertListener.complete(isSuccess);
        }
    }
}
