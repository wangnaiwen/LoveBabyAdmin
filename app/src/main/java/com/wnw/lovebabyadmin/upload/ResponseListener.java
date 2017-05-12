package com.wnw.lovebabyadmin.upload;

import com.android.volley.Response;

public interface ResponseListener extends Response.ErrorListener {
    public void onResponse(String response);
}
