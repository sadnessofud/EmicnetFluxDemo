package com.emicnet.emicallwebapi.voiceNotify;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.main.EmicallWebApiApplication;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.uploadText.UploadTextRequestBody;
import com.emicnet.emicallwebapi.remote.bean.uploadText.UploadTextResponse;
import com.emicnet.emicallwebapi.remote.bean.voiceNotify.VoiceNotifyRequestBody;
import com.emicnet.emicallwebapi.remote.bean.voiceNotify.VoiceNotifyResponse;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShengWang on 2017/6/14.
 */

public class VoiceNotifyActionCreator {
    private final String TAG = "NotifyActionCreator";
    private static VoiceNotifyActionCreator instance;
    private Dispatcher mDispatcher;

    VoiceNotifyActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static VoiceNotifyActionCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new VoiceNotifyActionCreator(dispatcher);
        }
        return instance;
    }

    public void requestVoiceNotify(Context context, VoiceNotifyRequestBody voiceNotifyRequestBody){
        UserInfo userInfo = UserInfo.get();
        String url = new URI.Builder().buildRequestPath("SubAccounts")
                .buildSid(userInfo.subAccountSid)
                .buildToken(userInfo.subAccountToken)
                .buildSigAndAuth()
                .buildFunction(Function.VOICE_NOTIFY)
                .buildHttpsUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", voiceNotifyRequestBody.appId);
            sub.put("textId", voiceNotifyRequestBody.voiceId);
            sub.put("to", voiceNotifyRequestBody.to);
            object.put("voiceNotify", sub);
            Log.i(TAG, "requestVoiceNotify:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final VoiceNotifyResponse voiceCodeResponse = new VoiceNotifyResponse();
        JsonObjectRequest request = WebRequest.request(url, object, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i(TAG,"onSuccess:"+response.toString());
                voiceCodeResponse.status = 0;
                try {
                    if(response !=null){
                        Log.i(TAG,"respCode");
                        if(response.has("resp")){
                            JSONObject resp = response.getJSONObject("resp");
                            if(resp.has("respCode")){
                                voiceCodeResponse.respCode = resp.getInt("respCode");
                                Log.i(TAG,"respCode:"+ voiceCodeResponse.respCode );
                            }
                        }

                    }

                }catch  (JSONException e) {
                    e.printStackTrace();
                }

                mDispatcher.dispatch(new VoiceNotifyAction(VoiceNotifyAction.REQUEST_VOICE_NOTIFY,new VoiceNotify(voiceCodeResponse)));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG,"onFailure:"+error.toString());
                voiceCodeResponse.status = -1;
                mDispatcher.dispatch(new VoiceNotifyAction(VoiceNotifyAction.REQUEST_VOICE_NOTIFY,new VoiceNotify(voiceCodeResponse)));
            }
        });


        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }

    public void uploadText(final Context context, final UploadTextRequestBody requestBody, final String to){
        UserInfo userInfo = UserInfo.get();
        String url = new URI.Builder().buildRequestPath("Accounts")
                .buildSid(userInfo.accountSid)
                .buildToken(userInfo.authToken)
                .buildSigAndAuth()
                .buildFunction(Function.UPLOAD_TEXT)
                .buildHttpsUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", requestBody.appId);
            sub.put("text", requestBody.text);
            sub.put("maxAge", requestBody.maxAge);
            object.put("uploadText", sub);
            Log.i(TAG, "UploadTextRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final UploadTextResponse uploadTextResponse = new UploadTextResponse();
        JsonObjectRequest request = WebRequest.request(url, object, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i(TAG,"onSuccess:"+response.toString());
                uploadTextResponse.status = 0;
                try {
                    if(response !=null){
                        Log.i(TAG,"respCode");
                        if(response.has("resp")){
                            JSONObject resp = response.getJSONObject("resp");
                            if(resp.has("respCode")){
                                uploadTextResponse.respCode = resp.getInt("respCode");
                                Log.i(TAG,"respCode:"+ uploadTextResponse.respCode );
                            }
                            JSONObject uploadText = resp.getJSONObject("uploadText");
                            uploadTextResponse.textId = uploadText.getInt("textId");
                            mDispatcher.dispatch(new VoiceNotifyAction(VoiceNotifyAction.UPLOAD_TEXT,new VoiceNotify(uploadTextResponse)));
                            VoiceNotifyRequestBody voiceNotifyRequestBody = new VoiceNotifyRequestBody(requestBody.appId, uploadTextResponse.textId, to);
                            requestVoiceNotify(context, voiceNotifyRequestBody);
                        }

                    }

                }catch  (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG,"onFailure:"+error.toString());
                uploadTextResponse.status = -1;
                mDispatcher.dispatch(new VoiceNotifyAction(VoiceNotifyAction.UPLOAD_TEXT,new VoiceNotify(uploadTextResponse)));
            }
        });
        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }

}
