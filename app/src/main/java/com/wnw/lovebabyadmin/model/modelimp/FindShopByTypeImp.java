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
import com.wnw.lovebabyadmin.domain.Shop;
import com.wnw.lovebabyadmin.model.imodel.IFindShopByType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public class FindShopByTypeImp implements IFindShopByType {
    private Context context;
    private ShopFindByTypeListener shopFindByTypeListener;
    private List<Shop> shops;

    @Override
    public void findShop(Context context, int type, int page, ShopFindByTypeListener shopFindByTypeListener) {
        this.context = context;
        this.shopFindByTypeListener = shopFindByTypeListener;
        sendRequestWithVolley(type, page);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(int type, int page){
        String url = NetConfig.SERVICE + NetConfig.FIND_SHOP_BY_TYPE;
        url = url + "page=" + page
        +"&type=" + type;
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
            JSONArray jsonArray = jsonObject.getJSONArray("findShopByType");
            if(jsonArray != null){
                shops = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Shop shop = new Shop();
                    shop.setId(object.getInt("id"));
                    shop.setName(object.getString("name"));
                    shop.setOwner(object.getString("owner"));
                    shop.setUserId(object.getInt("userId"));
                    shop.setMoney(object.getLong("money"));
                    shop.setWechat(object.getString("wechat"));
                    shop.setIdCard(object.getString("idCard"));
                    shop.setReviewType(object.getInt("reviewType"));
                    shop.setInvitee(object.getInt("invitee"));
                    shops.add(shop);
                }
            }else {
                Toast.makeText(context, "找不到", Toast.LENGTH_SHORT).show();
                shops = null;
            }
        }catch (JSONException e){
            shops = null;
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
        if(shopFindByTypeListener != null){
            shopFindByTypeListener.complete(shops);
        }
    }
}
