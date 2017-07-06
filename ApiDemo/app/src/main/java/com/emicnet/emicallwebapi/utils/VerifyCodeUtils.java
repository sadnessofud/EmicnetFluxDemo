package com.emicnet.emicallwebapi.utils;

import android.util.Log;

import java.util.Random;

/**
 * Created by ShengWang on 2017/5/31.
 */

public class VerifyCodeUtils {

    private static final String TAG = "VerifyCodeUtils";

    public static int getVerifyCode(){
        int num;
        Random random = new Random();
        num = random.nextInt(9000) + 1000;
        Log.i(TAG, "getVeirifyCode()...,num:" + num);
        return num;
    }
}
