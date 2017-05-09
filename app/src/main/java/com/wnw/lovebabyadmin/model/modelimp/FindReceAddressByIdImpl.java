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
import com.wnw.lovebabyadmin.domain.ReceAddress;
import com.wnw.lovebabyadmin.model.imodel.IFindReceAddressByIdModel;
import com.wnw.lovebabyadmin.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by wnw on 2017/4/18.
 */

public class FindReceAddressByIdImpl implements IFindReceAddressByIdModel {

    private Context context;
    private ReceAddressFindByIdListener receAddressFindByIdListener;
    private ReceAddress returnData;

    @Override
    public void findReceAddressById(Context context, int id, ReceAddressFindByIdListener receAddressFindByIdListener) {
        this.receAddressFindByIdListener = receAddressFindByIdListener;
        this.context = context;
        sendRequestWithVolley(id);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int id){
        String url = NetConfig.SERVICE + NetConfig.FIND_RECE_ADDRESS_BY_ID
                +"id=" + id;

        LogUtil.d("url", url);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
            JSONObject object = jsonObject.getJSONObject("findReceAdd");
            if(object != null){
                returnData = new ReceAddress();
                returnData.setId(object.getInt("id"));
                returnData.setUserId(object.getInt("userId"));
                returnData.setReceiver(object.getString("receiver"));
                returnData.setProvince(object.getString("province"));
                returnData.setCity(object.getString("city"));
                returnData.setCounty(object.getString("county"));
                returnData.setDetailAddress(object.getString("detailAddress"));
                returnData.setPhone(object.getString("phone"));
                returnData.setPostcode(object.getInt("postcode"));
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(receAddressFindByIdListener != null){
            receAddressFindByIdListener.complete(returnData);
        }
    }
}
