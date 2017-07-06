package com.emicnet.emicallwebapi.remote.bean.teleConference;

/**
 * Created by ShengWang on 2017/6/13.
 */

public class TelConferenceRequestBody {
    public String appId;
    public String from;
    public String to;
    public String userData;

    public TelConferenceRequestBody(String appId, String from, String to) {
        this.appId = appId;
        this.from = from;
        this.to = to;
    }

    public TelConferenceRequestBody(String appId, String from, String to, String userData) {
        this.appId = appId;
        this.from = from;
        this.to = to;
        this.userData = userData;
    }
}
