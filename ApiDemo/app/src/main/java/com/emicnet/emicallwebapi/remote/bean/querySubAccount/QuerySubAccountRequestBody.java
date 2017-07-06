package com.emicnet.emicallwebapi.remote.bean.querySubAccount;

/**
 * Created by ShengWang on 2017/6/16.
 */

/*
{
    "subAccountList" : {
        "appId" : " de455f49db791544e7ca25d8bbe86c3a"
    }
}
 */
public class QuerySubAccountRequestBody {
    public String appId;

    public QuerySubAccountRequestBody(String appId) {
        this.appId = appId;
    }
}
