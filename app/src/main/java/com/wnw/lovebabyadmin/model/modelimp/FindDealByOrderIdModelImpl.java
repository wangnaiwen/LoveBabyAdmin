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
import com.wnw.lovebabyadmin.domain.Deal;
import com.wnw.lovebabyadmin.model.imodel.IFindDealByOrderIdModel;
import com.wnw.lovebabyadmin.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindDealByOrderIdModelImpl implements IFindDealByOrderIdModel {

    private Context context;
    private IFindDealByOrderIdModel.DealFindByOrderIdListener dealFindByOrderIdListener;
    private List<Deal> returnData;

    @Override
    public void findDealByOrderId(Context context, int orderId, DealFindByOrderIdListener dealFindByOrderIdListener) {
        this.context = context;
        this.dealFindByOrderIdListener = dealFindByOrderIdListener;
        sendRequestWithVolley(orderId);
    }

    private void sendRequestWithVolley(int orderId){
        String url = NetConfig.SERVICE+NetConfig.FIND_DEAL_BY_ORDER_ID
                +"orderId="+orderId;
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
            JSONArray jsonArray = jsonObject.getJSONArray("findDealByOrderId");
            if(jsonArray != null){
                returnData = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Deal deal = new Deal();
                    deal.setId(object.getInt("id"));
                    deal.setOrderId(object.getInt("orderId"));
                    deal.setProductId(object.getInt("productId"));
                    deal.setProductCount(object.getInt("productCount"));
                    deal.setSumPrice(object.getLong("sumPrice"));
                    deal.setProductName(object.getString("productName"));
                    returnData.add(deal);
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
        if(dealFindByOrderIdListener != null){
            dealFindByOrderIdListener.complete(returnData);
        }
    }
}
