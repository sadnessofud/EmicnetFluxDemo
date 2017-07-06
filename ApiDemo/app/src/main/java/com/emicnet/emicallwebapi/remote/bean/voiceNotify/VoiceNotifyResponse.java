package com.emicnet.emicallwebapi.remote.bean.voiceNotify;

/**
 * Created by ShengWang on 2017/6/14.
 */

/*{
"resp" : {
"respCode" : "000000",
"voiceNotify" : {
"callId" : "api1234059445aDbbJxIdbT",
"status" : "0"
}
}
}
*/
public class VoiceNotifyResponse {
    public int respCode;
    public String callId;
    public int status;
}
