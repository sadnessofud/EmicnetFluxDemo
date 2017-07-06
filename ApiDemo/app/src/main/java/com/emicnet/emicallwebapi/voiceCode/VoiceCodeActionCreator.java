package com.emicnet.emicallwebapi.voiceCode;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.main.EmicallWebApiApplication;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.voiceCode.VoiceCodeRequestBody;
import com.emicnet.emicallwebapi.remote.bean.voiceCode.VoiceCodeResponse;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by charlie on 2017/6/8.
 */

public class VoiceCodeActionCreator {
    private final String TAG = "VoiceCodeActionCreator";
    private static VoiceCodeActionCreator instance;
    private Dispatcher mDispatcher;

    VoiceCodeActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static VoiceCodeActionCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new VoiceCodeActionCreator(dispatcher);
        }
        return instance;
    }

    public void requestVoiceCode(Context context, VoiceCodeRequestBody voiceCodeRequestBody){
        String url = new URI.Builder().buildRequestPath("SubAccounts")
                .buildSid(UserInfo.get().subAccountSid)
                .buildToken(UserInfo.get().subAccountToken)
                .buildSigAndAuth()
                .buildFunction(Function.VOICE_CODE)
                .buildHttpUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", voiceCodeRequestBody.appId);
            sub.put("verifyCode", voiceCodeRequestBody.verifyCode);
            sub.put("to", voiceCodeRequestBody.to);
            object.put("voiceCode", sub);
            Log.i(TAG, "VoiceRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final VoiceCodeResponse voiceCodeResponse = new VoiceCodeResponse();
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

                mDispatcher.dispatch(new VoiceCodeAction(VoiceCodeAction.GET_VOICE_CODE,voiceCodeResponse));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG,"onFailure:"+error.toString());
                voiceCodeResponse.status = -1;
                mDispatcher.dispatch(new VoiceCodeAction(VoiceCodeAction.GET_VOICE_CODE,voiceCodeResponse));
            }
        });


        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }


}
