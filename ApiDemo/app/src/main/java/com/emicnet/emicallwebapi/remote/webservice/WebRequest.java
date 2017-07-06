package com.emicnet.emicallwebapi.remote.webservice;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.model.Callback;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by charlie on 2017/6/9.
 */

public class WebRequest {
    private static final String TAG = "VoiceCodeWebRequest";

    static public JsonObjectRequest request(String url, final JSONObject requestBody , final Callback callback) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, response.toString());
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
                callback.onFailure(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json;charset=utf-8");
                params.put("Authorization", URI.AUTH);
                Log.i(TAG, "getHeaders()...,AUTH:" + URI.AUTH);
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        return request;
    }
}
