package com.emicnet.emicallwebapi.remote.bean.uploadText;

/**
 * Created by ShengWang on 2017/6/14.
 */

/*{
"uploadText" : {
"appId" : "b23abb6d451346efa13370172d1921ef",
"text" : "测试文本",
"maxAge" : "1800"
}
}
*/
public class UploadTextRequestBody {
    public String appId;
    public String text;
    public int maxAge;

    public UploadTextRequestBody(String appId, String text) {
        this.appId = appId;
        this.text = text;
    }

    public UploadTextRequestBody(String appId, String text, int maxAge) {
        this.appId = appId;
        this.text = text;
        this.maxAge = maxAge;
    }
}
