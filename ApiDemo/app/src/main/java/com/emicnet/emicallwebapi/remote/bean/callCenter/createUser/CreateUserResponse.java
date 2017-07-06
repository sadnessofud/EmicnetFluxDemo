package com.emicnet.emicallwebapi.remote.bean.callCenter.createUser;

import com.emicnet.emicallwebapi.remote.bean.RESPONSE;

/**
 * Created by ShengWang on 2017/6/16.
 */

/*
{
"resp" : {
"respCode" : "000000",
"createUser" : {
"number" : “5032”
}
}
}
 */
public class CreateUserResponse {
    public int status = RESPONSE.DEFAULT;
    public int respCode;
    public int number;
}
