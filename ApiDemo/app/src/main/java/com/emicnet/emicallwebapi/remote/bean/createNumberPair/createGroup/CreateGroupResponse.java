package com.emicnet.emicallwebapi.remote.bean.createNumberPair.createGroup;

import com.emicnet.emicallwebapi.remote.bean.RESPONSE;

/**
 * Created by ShengWang on 2017/6/15.
 */

/*
{
"resp" : {
"respCode" : "000000",
"createGroup" : {
"gid" : "2"
}
}
}
 */
public class CreateGroupResponse {
    public int status = RESPONSE.DEFAULT;//0:成功，-1：失敗 -2：默認值}
    public int respCode;
    public int gid;
}
