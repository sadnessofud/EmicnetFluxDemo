package com.emicnet.emicallwebapi.remote.bean.callCenter.callOut;

/**
 * Created by ShengWang on 2017/6/27.
 */

public class CallOutRequestBody {
    public String appId;
    public String workNumber;
    public String to;
    public String userData;

    public CallOutRequestBody(String appId, String workNumber, String to) {
        this.appId = appId;
        this.workNumber = workNumber;
        this.to = to;
    }

    public CallOutRequestBody(String appId, String workNumber, String to, String userData) {
        this.appId = appId;
        this.workNumber = workNumber;
        this.to = to;
        this.userData = userData;
    }
}
