package com.emicnet.emicallwebapi.main;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;

/**
 * Created by ShengWang on 2017/5/27.
 */

public class EmicallWebApiApplication extends Application{
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        UserInfo userInfo = UserInfo.get();
        userInfo.accountSid = "ae7ea9ce665354b3fdcc555f35e7afbf";
        userInfo.authToken = "2b93cf280fc890ad12310c153cbdeb33";
        userInfo.subAccountSid = "f99580e7dcca8ab27f30f32db76d8ae2";
        userInfo.subAccountToken = "514a468d1ab5d96742795ea13e58eaa3";
        userInfo.appId = "5c3c2a3d501be4fe4796c9a14d0ecb3e";
        super.onCreate();
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            synchronized (EmicallWebApiApplication.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                }
            }
        }
        return mRequestQueue;
    }

    public void addToRequestQueue(Request request){
        getRequestQueue().add(request);
    }
}
