package com.emicnet.emicallwebapi.remote.bean.createNumberPair.matchNumber;

import com.emicnet.emicallwebapi.remote.bean.RESPONSE;

/**
 * Created by ShengWang on 2017/6/15.
 */

/*
{
    "resp"       : {
        "respCode"   : "000000",
        "createNumberPair"   :  {
            "useNumber"     : "02566687987"
        }
    }
}
 */
public class MatchNumberResponse {
    public int status = RESPONSE.DEFAULT;//0:成功，-1：失敗 -2：默認值
    public int respCode;
    public String userNumber;
}
