package com.emicnet.emicallwebapi.remote.bean.voiceCode;

import com.emicnet.emicallwebapi.remote.bean.RESPONSE;

/**
 * Created by charlie on 2017/6/9.
 */
/*
{
        "resp" : {
        "respCode" : "000000",
        "voiceCode" : {
        "callId" : "api1234059445aDbbJxIdbT",
        "createTime" : "20150829193453"
        }
        }
        }
*/

public class VoiceCodeResponse {

    public int status = RESPONSE.DEFAULT;//0:成功，-1：失敗 -2：默認值
    public int respCode;
    public String callId;
    public String createTime;
}
