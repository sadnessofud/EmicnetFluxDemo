package com.emicnet.emicallwebapi.remote.bean.createUser;

/**
 * Created by ShengWang on 2017/6/16.
 */

/*
{
"createUser" : {
"appId" : "b23abb6d451346efa13370172d1921ef",
"workNumber" : "000000001",
"phone" : "13587384765",
"displayName" : "test",
"directNumber" : "02566699808",
"callTime" : "100"
}
}
 */
public class CreateuserRequestBody {
    public String appId;
    public String workNumber;
    public String phone;
    public String displayName;
    public String directNumber;
    public int callTime;

    public CreateuserRequestBody(String appId, String workNumber, String phone) {
        this.appId = appId;
        this.workNumber = workNumber;
        this.phone = phone;
    }
}
