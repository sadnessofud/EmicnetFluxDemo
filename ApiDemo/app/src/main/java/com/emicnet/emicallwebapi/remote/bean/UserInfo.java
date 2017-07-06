package com.emicnet.emicallwebapi.remote.bean;

/**
 * Created by ShengWang on 2017/6/14.
 */

public class UserInfo {
    private static UserInfo instance;
    public static UserInfo get(){
        if (instance == null){
            synchronized (UserInfo.class){
                if (instance == null){
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }
    private UserInfo(){};
    public String accountSid;
    public String authToken;
    public String subAccountSid;
    public String subAccountToken;
    public String appId;
}
