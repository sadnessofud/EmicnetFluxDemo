package com.emicnet.emicallwebapi.remote.bean.voiceCode;

/**
 * Created by charlie on 2017/6/9.
 */
/*
{
        "voiceCode" : {
        "appId" : "b23abb6d451346efa13370172d1921ef",
        "verifyCode" : "3721",
        "to" : "15689089874",
        "userData" : "12345678"
        }
        }
*/

public class VoiceCodeRequestBody {
    public String appId;
    public int verifyCode;
    public String to;
    public String userData;

    public VoiceCodeRequestBody(String appId, int verifyCode, String to, String userData) {
        this.appId = appId;
        this.verifyCode = verifyCode;
        this.to = to;
        this.userData = userData;
    }

    public VoiceCodeRequestBody(String appId, int verifyCode, String to) {
        this.appId = appId;
        this.verifyCode = verifyCode;
        this.to = to;
    }
}
