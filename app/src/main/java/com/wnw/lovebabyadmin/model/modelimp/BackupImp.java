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
import com.wnw.lovebabyadmin.model.imodel.IBackupModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/6/4.
 */

public class BackupImp implements IBackupModel{
    private Context context;
    private BackupListener backupListener;
    private boolean isSuccess;

    @Override
    public void backup(Context context, BackupListener backupListener) {
        this.context = context;
        this.backupListener = backupListener;
        sendRequestWithVolley();
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(){
        String url = NetConfig.SERVICE + NetConfig.BACKUP;
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
            isSuccess = jsonObject.getBoolean("backup");
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
        if(backupListener != null){
            backupListener.complete(isSuccess);
        }
    }
}
