package com.emicnet.emicallwebapi.remote.bean;

import android.util.Log;

import com.emicnet.emicallwebapi.utils.EncodeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ShengWang on 2017/5/27.
 */

public class URI {
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    public static String DOMAIN = "apitest.emic.com.cn";
    public static String VERSION = "20161206";
    public static String REQUEST_PATH = "";
    public static String SID = "";
    public static String TOKEN = "";
    public static String FUNCTION = "";
    public static String SIG = "";
    public static String AUTH = "";


    public static class Builder {
        private static final String TAG = "ApiModel";


        public Builder buildDomain(String domain) {
            DOMAIN = domain;
            return this;
        }

        public Builder buildVersion(String version) {
            VERSION = version;
            return this;
        }

        public Builder buildRequestPath(String requestPath) {
            REQUEST_PATH = requestPath;
            return this;
        }

        public Builder buildSid(String sid) {
            SID = sid;
            return this;
        }

        public Builder buildToken(String token) {
            TOKEN = token;
            return this;
        }

        public Builder buildFunction(String function) {
            FUNCTION = function;
            return this;
        }

        public Builder buildSigAndAuth() {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = format.format(new Date());
            SIG = SID + TOKEN + time;
            String authorization = SID + ":" + time;
            AUTH = EncodeUtils.getBase64(authorization);
            Log.i(TAG, "buildSigAndAuth()...,AUTH:" + AUTH);
            return this;
        }

        public String buildHttpsUrl() {
            String url = HTTPS + DOMAIN + "/" + VERSION + "/" + REQUEST_PATH + "/" + SID + "/" + FUNCTION
                    + "?sig=" + EncodeUtils.getMd5(SIG).toUpperCase();
            Log.i(TAG, "buildHttpsUrl:" + url);
            return url;
        }

        public String buildHttpUrl() {
            String url = HTTP + DOMAIN + "/" + VERSION + "/" + REQUEST_PATH + "/" + SID + "/" + FUNCTION
                    + "?sig=" + EncodeUtils.getMd5(SIG).toUpperCase();
            Log.i(TAG, "buildHttpUrl:" + url);
            return url;
        }
    }
}
