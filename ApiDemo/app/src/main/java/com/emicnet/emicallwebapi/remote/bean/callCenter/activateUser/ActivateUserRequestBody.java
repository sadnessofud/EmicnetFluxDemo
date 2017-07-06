package com.emicnet.emicallwebapi.remote.bean.callCenter.activateUser;

/**
 * Created by ShengWang on 2017/6/16.
 */

/*
{
"signIn" : {
"appId" : "b23abb6d451346efa13370172d1921ef",
"workNumber" : "00000001",
"deviceNumber" : "18769809876"
}
}
 */
public class ActivateUserRequestBody {
    public String appId;
    public String workNumber;
    public String deviceNumber;

    public ActivateUserRequestBody(String appId, String workNumber, String deviceNumber) {
        this.appId = appId;
        this.workNumber = workNumber;
        this.deviceNumber = deviceNumber;
    }
}
