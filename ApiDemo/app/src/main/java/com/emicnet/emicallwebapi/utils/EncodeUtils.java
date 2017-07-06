package com.emicnet.emicallwebapi.utils;

import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ShengWang on 2017/5/27.
 */

public class EncodeUtils {
    private static final String TAG = "UrlHelper";
    public static String domain = "https://apitest.emic.com.cn";
    public static String version = "20161206";
    public static String requestPath = "Accounts";
    public static String sid = "b4ffd69fbe3e1b4292ca6484394f78fd";
    public static String token = "78289bfdcdfb5f0403967c43e8fe5ea5";
    public static String function = "Applications/subAccountList";
    public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    public static String time = format.format(new Date());
    public static String sig = "";
    public static String sigParameter = "?sig=" + getMd5(sid).toUpperCase();

    public static String getSig(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(new Date());
        String sig = sid + token + time;
        return sig;
    }

    public static String buildUrl(){
        String url = domain + "/" + version + "/" + requestPath + "/" + sid + "/" + function
                + sigParameter;
        Log.i(TAG, "buildUrl:" + url);
        return  url;
    }

    public static String getBase64(String uid) {
        Log.d("TAG", "makeUidToBase64 uid = " + uid);
        String strUid = String.valueOf(uid);
        String enUid = new String(Base64.encode(strUid.getBytes(), Base64.DEFAULT));
        Log.d("TAG", "makeUidToBase64 enUid = " + enUid);
        return enUid;
    }

    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}
