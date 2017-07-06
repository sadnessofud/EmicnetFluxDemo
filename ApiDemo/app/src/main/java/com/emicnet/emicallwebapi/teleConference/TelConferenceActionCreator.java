package com.emicnet.emicallwebapi.teleConference;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.main.EmicallWebApiApplication;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.teleConference.TelConferenceRequestBody;
import com.emicnet.emicallwebapi.remote.bean.teleConference.TelConferenceResponse;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShengWang on 2017/6/13.
 */

public class TelConferenceActionCreator {
    private final String TAG = "TwoWayCallActionCreator";
    private static TelConferenceActionCreator instance;
    private Dispatcher mDispatcher;

    TelConferenceActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static TelConferenceActionCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new TelConferenceActionCreator(dispatcher);
        }
        return instance;
    }

    public void requestTwoWayCall(Context context, TelConferenceRequestBody telConferenceRequestBody){
        String url = new URI.Builder().buildRequestPath("SubAccounts")
                .buildSid(UserInfo.get().subAccountSid)
                .buildToken(UserInfo.get().subAccountToken)
                .buildSigAndAuth()
                .buildFunction(Function.CALLBACK)
                .buildHttpUrl();
        Log.i(TAG, "url:" + url);
        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();
        try {
            sub.put("appId", telConferenceRequestBody.appId);
            sub.put("from", telConferenceRequestBody.from);
            sub.put("to", telConferenceRequestBody.to);
            object.put("callBack", sub);
            Log.i(TAG, "twoWayCallRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final TelConferenceResponse twoWayCallResponse = new TelConferenceResponse();
        JsonObjectRequest request = WebRequest.request(url, object, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i(TAG,"onSuccess:"+response.toString());
                twoWayCallResponse.status = 0;
                try {
                    if(response !=null){
                        Log.i(TAG,"respCode");
                        if(response.has("resp")){
                            JSONObject resp = response.getJSONObject("resp");
                            if(resp.has("respCode")){
                                twoWayCallResponse.respCode = resp.getInt("respCode");
                                Log.i(TAG,"respCode:"+ twoWayCallResponse.respCode );
                            }
                        }

                    }

                }catch  (JSONException e) {
                    e.printStackTrace();
                }

                mDispatcher.dispatch(new TelConferenceAction(TelConferenceAction.REQUEST_TEL_CONFERENCE,twoWayCallResponse));
            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG,"onFailure:"+error.toString());
                twoWayCallResponse.status = -1;
                mDispatcher.dispatch(new TelConferenceAction(TelConferenceAction.REQUEST_TEL_CONFERENCE,twoWayCallResponse));
            }
        });
        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }
}
