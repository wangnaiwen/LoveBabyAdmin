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
import com.wnw.lovebabyadmin.domain.Mc;
import com.wnw.lovebabyadmin.model.imodel.IFindMcByIdModel;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by wnw on 2017/4/20.
 */

public class FindMcByIdImpl implements IFindMcByIdModel {

    private Context context;
    private FindMcByIdListener findMcByIdListener;
    private Mc mc;

    @Override
    public void findMcById(Context context, int id, FindMcByIdListener findMcByIdListener) {
        this.context = context;
        this.findMcByIdListener = findMcByIdListener;
        sendRequestWithVolley(id);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int id){
        String url = NetConfig.SERVICE+ NetConfig.FIND_MC_BY_ID + "id="+id;

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
            JSONObject object = jsonObject.getJSONObject("findMcById");
            if(object != null){
                mc = new Mc();
                mc.setId(object.getInt("id"));
                mc.setName(object.getString("name"));
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
        if(findMcByIdListener != null){
            findMcByIdListener.complete(mc);
        }
    }
}
