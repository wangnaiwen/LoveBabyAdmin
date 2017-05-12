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
import com.wnw.lovebabyadmin.model.imodel.IUpdateArticle;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/5/8.
 */

public class UpdateArticleImp implements IUpdateArticle {
    private Context context;
    private ArticleUpdateListener articleUpdateListener;
    private boolean isSuccess;


    @Override
    public void updateArticle(Context context, Article article, ArticleUpdateListener articleUpdateListener) {
        this.context = context;
        this.articleUpdateListener = articleUpdateListener;
        sendRequestWithVolley(article);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(Article article){

        String author = "";
        String title = "";
        String content = "";
        String coverImg = "";
        try {
            author = URLEncoder.encode(article.getAuthor(), "UTF-8");
            title = URLEncoder.encode(article.getTitle(), "UTF-8");
            content = URLEncoder.encode(article.getContent(), "UTF-8");
            coverImg = URLEncoder.encode(article.getCoverImg(), "UTF-8");
        }catch (Exception e){

        }

        String url = NetConfig.SERVICE + NetConfig.UPDATE_ARTICLE;
        url = url +"id="+article.getId()
                +"&author=" + author
                +"&title=" + title
                +"&content=" + content
                +"&coverImg=" + coverImg;
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
            isSuccess = jsonObject.getBoolean("updateArticle");
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
        if(articleUpdateListener != null){
            articleUpdateListener.complete(isSuccess);
        }
    }
}
