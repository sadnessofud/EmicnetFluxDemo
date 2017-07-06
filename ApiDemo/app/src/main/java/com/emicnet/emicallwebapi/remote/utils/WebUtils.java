package com.emicnet.emicallwebapi.remote.utils;

/**
 * Created by ShengWang on 2017/6/27.
 */

public class WebUtils {

    public static boolean isRequestRealSuc(int respCode) {
        return respCode == 0;
    }
}
