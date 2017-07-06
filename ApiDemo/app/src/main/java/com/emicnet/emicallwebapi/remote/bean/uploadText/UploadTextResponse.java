package com.emicnet.emicallwebapi.remote.bean.uploadText;

import com.emicnet.emicallwebapi.remote.bean.RESPONSE;

/**
 * Created by ShengWang on 2017/6/14.
 */

/*
{
"resp" : {
"respCode" : "000000",
"uploadText" : {
"textId" : "12"
}
}
}
 */
public class UploadTextResponse {
    public int status = RESPONSE.DEFAULT;//0:成功，-1：失敗 -2：默認值
    public int respCode;
    public int textId;
}
