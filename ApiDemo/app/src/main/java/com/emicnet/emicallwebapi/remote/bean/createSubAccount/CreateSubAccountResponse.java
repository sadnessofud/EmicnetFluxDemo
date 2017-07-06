package com.emicnet.emicallwebapi.remote.bean.createSubAccount;

import com.emicnet.emicallwebapi.remote.bean.RESPONSE;

/**
 * Created by ShengWang on 2017/6/16.
 */

/*
{
    "resp"       : {
        "respCode"   : "000000",
        "createSubAccount"   : {
        "appId"      : "b23abb6d451346efa13370172d1921ef",
            "subAccountSid" : “c5dc4b87f33ef2ef37c8e974793ad8e5”,
            "subAccountToken" : “16ae80709755b300e8016775722d112c”
        }
    }
}
 */
public class CreateSubAccountResponse {
    public int status = RESPONSE.DEFAULT;
    public int respCode;
    public String appId;
    public String subAccountSid;
    public String subAccountToken;
}
