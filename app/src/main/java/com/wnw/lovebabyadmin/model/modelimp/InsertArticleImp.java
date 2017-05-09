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
import com.wnw.lovebabyadmin.model.imodel.IInsertArticle;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/5/8.
 */

public class InsertArticleImp implements IInsertArticle {

    private Context context;
    private ArticleInsertListener articleInsertListener;
    private boolean isSuccess;

    @Override
    public void insertArticle(Context context, Article article, ArticleInsertListener articleInsertListener) {
        this.context = context;
        this.articleInsertListener = articleInsertListener;
        sendRequestWithVolley(article);
    }


    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(Article article){

        String content = "";
        try {
            content = URLEncoder.encode(article.getContent(), "UTF-8");
        }catch (Exception e){

        }

        String url = NetConfig.SERVICE + NetConfig.INSERT_ARTICLE;
        url = url + "author=" + article.getAuthor()
        +"&title=" + article.getTitle()
        +"&content=" + content
        +"&coverImg" + article.getCoverImg();
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
            isSuccess = jsonObject.getBoolean("insertArticle");
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
        if(articleInsertListener != null){
            articleInsertListener.complete(isSuccess);
        }
    }
}
