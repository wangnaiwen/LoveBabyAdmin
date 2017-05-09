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
import com.wnw.lovebabyadmin.domain.Article;
import com.wnw.lovebabyadmin.model.imodel.IFindAllArticles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public class FindAllArticleImp implements IFindAllArticles {
    private Context context;
    private ArticleFindByPageListener articleFindByPageListener;
    private List<Article> articles;


    @Override
    public void findArticles(Context context, int page, ArticleFindByPageListener articleFindByPageListener) {
        this.context = context;
        this.articleFindByPageListener = articleFindByPageListener;
        sendRequestWithVolley(page);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(int page){

        String url = NetConfig.SERVICE + NetConfig.FIND_ALL_ARTICLE;
        url = url + "page=" + page;
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
            JSONArray jsonArray = jsonObject.getJSONArray("findAllArticle");
            if(jsonArray != null){
                articles = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Article article = new Article();
                    article.setId(object.getInt("id"));
                    article.setAuthor(object.getString("author"));
                    article.setTime(object.getString("time"));
                    article.setTitle(object.getString("title"));
                    article.setContent(object.getString("content"));
                    article.setCoverImg(object.getString("coverImg"));
                    article.setReadTimes(object.getInt("readTimes"));
                    article.setLikeTimes(object.getInt("likeTimes"));
                    articles.add(article);
                }
            }else {
                Toast.makeText(context, "找不到", Toast.LENGTH_SHORT).show();
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
        if(articleFindByPageListener != null){
            articleFindByPageListener.complete(articles);
        }
    }
}
