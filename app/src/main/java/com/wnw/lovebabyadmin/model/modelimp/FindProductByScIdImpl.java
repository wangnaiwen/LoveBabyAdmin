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
import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.model.imodel.IFindProductByScIdModel;
import com.wnw.lovebabyadmin.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public class FindProductByScIdImpl implements IFindProductByScIdModel{
    private Context context;
    private ProductFindByScIdListener productFindByScIdListener;
    private List<Product> productList;

    @Override
    public void findProductByScId(Context context, int scId, int number,ProductFindByScIdListener productFindByScIdListener) {
        this.context = context;
        this.productFindByScIdListener = productFindByScIdListener;
        sendRequestWithVolley(scId, number);
    }
    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int scId, int number){
        String url = NetConfig.SERVICE+NetConfig.FIND_PRODUCT_BY_SC_ID
                +"scId="+scId
                +"&number="+number;
        LogUtil.d("url", url);
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
            JSONArray jsonArray = jsonObject.getJSONArray("findProductByscId");
            if(jsonArray != null){
                productList = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Product product = new Product();
                    product.setId(object.getInt("id"));
                    product.setScId(object.getInt("scId"));
                    product.setRetailPrice(object.getInt("retailPrice"));
                    product.setStandardPrice(object.getInt("standardPrice"));
                    product.setDescription(object.getString("description"));
                    product.setName(object.getString("name"));
                    product.setCoverImg(object.getString("coverImg"));
                    product.setNumbering(object.getString("numbering"));
                    product.setBrand(object.getString("brand"));
                    productList.add(product);
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
        if(productFindByScIdListener != null){
            productFindByScIdListener.complete(productList);
        }
    }
}
