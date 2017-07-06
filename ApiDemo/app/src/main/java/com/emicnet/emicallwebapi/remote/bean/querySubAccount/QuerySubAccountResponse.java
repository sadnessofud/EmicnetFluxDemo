package com.emicnet.emicallwebapi.remote.bean.querySubAccount;

import com.emicnet.emicallwebapi.remote.bean.RESPONSE;

/**
 * Created by ShengWang on 2017/6/16.
 */

/*
{
    "resp": {
        "respCode": 0,
        "subAccountList": {
            "number": 2,
            "subAccounts":
            [
                {
                    "subAccountSid": "d297685e2a6ae8306cd7e73a5c27ada3",
                    "subAccountToken" :
                        "d2b02214511b13d32023477a0efc3529",
                    "provinceId"   : "1",
                    "enterpriseId" : "62"
                },
                {
                    "subAccountSid": "716d672831267f692a7541f4156392e4",
                    "subAccountToken":
                        "c6d7f51b96b0ed4c53dcbcf6338bd64a",
                    "provinceId"   : "1",
                    "enterpriseId" : "63"
                }
             ]
        }
    }
}
 */
public class QuerySubAccountResponse {
    public int status = RESPONSE.DEFAULT;
    public int respCode;
    public int number;
}
