package com.emicnet.emicallwebapi.remote.bean.createNumberPair.queryFreeNumbers;

import com.emicnet.emicallwebapi.remote.bean.RESPONSE;

/**
 * Created by ShengWang on 2017/6/15.
 */

/*
{
"resp" : {
"respCode" : "000000",
"freeNumbers" : {
"count": 4,
"numbers" :
[ "02566699809",
"02566699808",
"02566699714",
"02566699823"
]
}
}
}
 */
public class QueryFreeNumbersResponse {
    public int status = RESPONSE.DEFAULT;//0:成功，-1：失敗 -2：默認值
    public int respCode;
    public int count;
    public String numbers;
}
