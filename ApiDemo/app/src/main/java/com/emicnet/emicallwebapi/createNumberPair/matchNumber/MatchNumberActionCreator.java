package com.emicnet.emicallwebapi.createNumberPair.matchNumber;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.main.EmicallWebApiApplication;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.matchNumber.MatchNumberRequestBody;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.matchNumber.MatchNumberResponse;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class MatchNumberActionCreator {
    private final String TAG = "VoiceCodeActionCreator";
    private static MatchNumberActionCreator instance;
    private Dispatcher mDispatcher;

    MatchNumberActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static MatchNumberActionCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new MatchNumberActionCreator(dispatcher);
        }
        return instance;
    }

    public void requestCreateNumberPair(Context context, MatchNumberRequestBody matchNumberRequestBody){
        String url = new URI.Builder().buildRequestPath("SubAccounts")
                .buildSid(UserInfo.get().subAccountSid)
                .buildToken(UserInfo.get().subAccountToken)
                .buildSigAndAuth()
                .buildFunction(Function.CREATE_NUMBER_PAIR)
                .buildHttpUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", matchNumberRequestBody.appId);
            sub.put("numberA", matchNumberRequestBody.numberA);
            sub.put("numberB", matchNumberRequestBody.numberB);
            object.put("createNumberPair", sub);
            Log.i(TAG, "VoiceRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MatchNumberResponse voiceCodeResponse = new MatchNumberResponse();
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

                mDispatcher.dispatch(new MatchNumberAction(MatchNumberAction.MATCH_NUMBER_PAIR,voiceCodeResponse));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG,"onFailure:"+error.toString());
                voiceCodeResponse.status = -1;
                mDispatcher.dispatch(new MatchNumberAction(MatchNumberAction.MATCH_NUMBER_PAIR,voiceCodeResponse));
            }
        });


        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }
}
