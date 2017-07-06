package com.emicnet.emicallwebapi.remote.bean.createNumberPair.queryFreeNumbers;

/**
 * Created by ShengWang on 2017/6/15.
 */

/*{
"freeNumbers" : {
"appId" : "b23abb6d451346efa13370172d1921ef"
}
}
*/
public class QueryFreeNumbersRequestBody {
    public String appId;

    public QueryFreeNumbersRequestBody(String appId) {
        this.appId = appId;
    }
}
