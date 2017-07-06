package com.emicnet.emicallwebapi.remote.bean.voiceNotify;

/**
 * Created by ShengWang on 2017/6/14.
 */

/*
{
"voiceNotify" : {
"appId" : "b23abb6d451346efa13370172d1921ef",
"voiceId" : "12",
"to" : "15689089874",
"userData" : "12345678"
}
}

 */
public class VoiceNotifyRequestBody {
    public String appId;
    public int voiceId;
    public String to;
    public String userData;

    public VoiceNotifyRequestBody(String appId, int voiceId, String to) {
        this.appId = appId;
        this.voiceId = voiceId;
        this.to = to;
    }
}
