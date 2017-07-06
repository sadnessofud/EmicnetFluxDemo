package com.emicnet.emicallwebapi.remote.bean.createSubAccount;

/**
 * Created by ShengWang on 2017/6/16.
 */

/*
{
    "createSubAccount"   : {
        "appId"      : "b23abb6d451346efa13370172d1921ef",
        "nickName"   : "test",
        "mobile"     : "15689089874",
        "email"      : "a@b.com"
    }
}
 */
public class CreateSubAccountRequestBody {
    public String appId;
    public String nickName;
    public String mobile;
    public String email;

    public CreateSubAccountRequestBody(String appId) {
        this.appId = appId;
    }

    public CreateSubAccountRequestBody(String appId, String nickName, String mobile, String email) {
        this.appId = appId;
        this.nickName = nickName;
        this.mobile = mobile;
        this.email = email;
    }
}
