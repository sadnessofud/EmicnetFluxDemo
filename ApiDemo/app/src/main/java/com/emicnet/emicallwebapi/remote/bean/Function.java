package com.emicnet.emicallwebapi.remote.bean;

/**
 * Created by ShengWang on 2017/5/25.
 */

public interface Function {
    String VOICE_CODE = "Calls/voiceCode";
    String CALLBACK = "Calls/callBack";
    String TEL_CONFERENE = "TELCONFERENE";
    String UPLOAD_TEXT = "Voice/uploadText";
    String VOICE_NOTIFY = "Calls/voiceNotify";
    String CREATE_NUMBER_PAIR = "Enterprises/createNumberPair";
    String CREATE_GROUP = "Enterprises/createGroup";
    String QUERY_FREE_NUMBERS = "Enterprises/freeNumbers";
    String CREATE_SUBACCOUNT = "Applications/createSubAccount";
    String QUERY_SUBACCOUNT = "Applications/subAccountList";
    String CALL_CENTER = "CALL_CENTER";
    String CREATE_USER = "Enterprises/createUser";
    String SIGN_IN = "CallCenter/signIn";
    String CALL_OUT = "CallCenter/callOut";
}
