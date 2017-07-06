package com.emicnet.emicallwebapi.remote.bean.twoWayCall;

import com.emicnet.emicallwebapi.remote.bean.RESPONSE;

/**
 * Created by ShengWang on 2017/6/12.
 */

public class TwoWayCallResponse {
    public int status = RESPONSE.DEFAULT;//0:成功，-1：失敗 -2：默認值
    public int respCode;
}
