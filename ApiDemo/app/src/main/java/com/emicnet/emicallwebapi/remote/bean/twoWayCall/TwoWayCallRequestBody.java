package com.emicnet.emicallwebapi.remote.bean.twoWayCall;

/**
 * Created by ShengWang on 2017/6/13.
 */
/*{
"callBack" : {
"appId" : "b23abb6d451346efa13370172d1921ef",
"from" : "13801289896",
"to" : "15689089874",
"userData" : "12345678"
}
}
*/
public class TwoWayCallRequestBody {
    public String appId;
    public String from;
    public String to;
    public String userData;

    public TwoWayCallRequestBody(String appId, String from, String to) {
        this.appId = appId;
        this.from = from;
        this.to = to;
    }

    public TwoWayCallRequestBody(String appId, String from, String to, String userData) {
        this.appId = appId;
        this.from = from;
        this.to = to;
        this.userData = userData;
    }
}
