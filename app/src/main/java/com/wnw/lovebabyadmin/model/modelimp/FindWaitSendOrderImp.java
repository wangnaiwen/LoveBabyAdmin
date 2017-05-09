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
import com.wnw.lovebabyadmin.domain.Order;
import com.wnw.lovebabyadmin.model.imodel.IFindWaitSendOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public class FindWaitSendOrderImp implements IFindWaitSendOrder {
    private Context context;
    private WaitOrderFindListener waitOrderFindListener;
    private List<Order> orders;

    @Override
    public void findWaitOrder(Context context, int type, int page, WaitOrderFindListener waitOrderFindListener) {
        this.context = context;
        this.waitOrderFindListener = waitOrderFindListener;
        sendRequestWithVolley(type, page);
    }

    /**
     * use volley to get the data
     * 这里要对Http进行Encode
     * */

    private void sendRequestWithVolley(int type, int page){
        String url = NetConfig.SERVICE + NetConfig.FIND_BE_SENT_ORDERS;
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
            JSONArray jsonArray = jsonObject.getJSONArray("findBeSentOrders");
            if(jsonArray != null){
                orders = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Order order = new Order();
                    order.setId(object.getInt("id"));
                    order.setShopId(object.getInt("shopId"));
                    order.setUserId(object.getInt("userId"));
                    order.setOrderNumber(object.getString("orderNumber"));
                    order.setOrderType(object.getInt("orderType"));
                    order.setCreateTime(object.getString("createTime"));
                    order.setPayTime(object.getString("payTime"));
                    order.setFinishTime(object.getString("finishTime"));
                    order.setAddressId(object.getInt("addressId"));
                    order.setRemark(object.getString("remark"));
                    orders.add(order);
                }
            }else {
                orders = null;
                Toast.makeText(context, "找不到", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            orders = null;
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
        if(waitOrderFindListener != null){
            waitOrderFindListener.complete(orders);
        }
    }
}
