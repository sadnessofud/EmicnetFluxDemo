package com.emicnet.emicallwebapi.remote.bean.createNumberPair.createGroup;

/**
 * Created by ShengWang on 2017/6/15.
 */

/*
{
"createGroup" : {
"appId" : "b23abb6d451346efa13370172d1921ef",
"groupName" : "售前组"
}
}
 */
public class CreateGroupRequestBody {
    public String appId;
    public String groupName;

    public CreateGroupRequestBody(String appId, String groupName) {
        this.appId = appId;
        this.groupName = groupName;
    }
}
