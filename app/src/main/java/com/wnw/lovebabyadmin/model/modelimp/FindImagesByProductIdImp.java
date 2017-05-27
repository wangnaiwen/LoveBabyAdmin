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
import com.wnw.lovebabyadmin.domain.ProductImage;
import com.wnw.lovebabyadmin.model.imodel.IFindProductImagesByProductId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/25.
 */

public class FindImagesByProductIdImp implements IFindProductImagesByProductId {
    private Context context;
    private FindImagesByProductIdListener findImagesByProductIdListener;
    private List<ProductImage> imageList;

    @Override
    public void findImagesByProductId(Context context, int productId, FindImagesByProductIdListener findImagesByProductIdListener) {
        this.context = context;
        this.findImagesByProductIdListener = findImagesByProductIdListener;
        sendRequestWithVolley(productId);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int productId){
        String url = NetConfig.SERVICE+NetConfig.FIND_PRODUCT_IMAGES_BY_PRODUCT_ID
                +"productId=" + productId;
        Log.d("url", url);
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
            JSONArray jsonArray = jsonObject.getJSONArray("findImagesByProductId");
            if(jsonArray != null){
                imageList = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length ; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    ProductImage image = new ProductImage();
                    image.setId(object.getInt("id"));
                    image.setProductId(object.getInt("productId"));
                    image.setPath(object.getString("path"));
                    imageList.add(image);
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
        if(findImagesByProductIdListener != null){
            findImagesByProductIdListener.complete(imageList);
        }
    }
}
