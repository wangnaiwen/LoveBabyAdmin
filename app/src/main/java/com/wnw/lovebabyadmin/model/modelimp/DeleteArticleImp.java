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
import com.wnw.lovebabyadmin.model.imodel.IAdminLogin;
import com.wnw.lovebabyadmin.model.imodel.IDeleteArticle;
import com.wnw.lovebabyadmin.util.Md5Encode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/5/8.
 */

public class DeleteArticleImp implements IDeleteArticle {

    private Context context;
    private ArticleDeleteListener articleDeleteListener;
    private boolean isSuccess;


    @Override
    public void deleteArticle(Context context, int id, ArticleDeleteListener articleDeleteListener) {
        this.context  =context;
        this.articleDeleteListener = articleDeleteListener;
        sendRequestWithVolley(id);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int id){
        String url = NetConfig.SERVICE + NetConfig.DELETE_ARTICLE;
        url = url + "id=" + id;
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
            isSuccess = jsonObject.getBoolean("deleteArticle");
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
        if(articleDeleteListener != null){
            articleDeleteListener.complete(isSuccess);
        }
    }
}
