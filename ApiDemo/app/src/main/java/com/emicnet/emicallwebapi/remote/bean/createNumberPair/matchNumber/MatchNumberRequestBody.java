package com.emicnet.emicallwebapi.remote.bean.createNumberPair.matchNumber;

/**
 * Created by ShengWang on 2017/6/15.
 */

/*
{
"createNumberPair" : {
"appId" : "b23abb6d451346efa13370172d1921ef",
"numberA" : "13587384765",
"numberB" : "13587384766"
}
}
 */
public class MatchNumberRequestBody {
    public String appId;
    public String numberA;
    public String numberB;

    public MatchNumberRequestBody(String appId, String numberA, String numberB) {
        this.appId = appId;
        this.numberA = numberA;
        this.numberB = numberB;
    }
}
