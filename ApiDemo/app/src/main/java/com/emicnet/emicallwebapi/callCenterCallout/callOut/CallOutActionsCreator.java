package com.emicnet.emicallwebapi.callCenterCallout.callOut;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.callCenterCallout.activateUser.ActivateUserAction;
import com.emicnet.emicallwebapi.callCenterCallout.activateUser.ActivateUserActionCreator;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.main.EmicallWebApiApplication;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.callCenter.activateUser.ActivateUserRequestBody;
import com.emicnet.emicallwebapi.remote.bean.callCenter.activateUser.ActivateUserResponse;
import com.emicnet.emicallwebapi.remote.bean.callCenter.callOut.CallOutRequestBody;
import com.emicnet.emicallwebapi.remote.bean.callCenter.callOut.CallOutResponse;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShengWang on 2017/6/27.
 */

public class CallOutActionsCreator {
    private final String TAG = "CallOutActionsCreator";
    private static CallOutActionsCreator instance;
    private Dispatcher mDispatcher;

    CallOutActionsCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static CallOutActionsCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new CallOutActionsCreator(dispatcher);
        }
        return instance;
    }

    public void requestCallOut(Context context, final CallOutRequestBody callOutRequestBody) {
        String url = new URI.Builder().buildRequestPath("SubAccounts")
                .buildSid(UserInfo.get().subAccountSid)
                .buildToken(UserInfo.get().subAccountToken)
                .buildSigAndAuth()
                .buildFunction(Function.CALL_OUT)
                .buildHttpUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", callOutRequestBody.appId);
            if (!TextUtils.isEmpty(callOutRequestBody.workNumber)) {
                sub.put("workNumber", callOutRequestBody.workNumber);
            }
            if (!TextUtils.isEmpty(callOutRequestBody.to)) {
                sub.put("to", callOutRequestBody.to);
            }
            object.put("callOut", sub);
            Log.i(TAG, "CallOutRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final CallOutResponse callOutResponse = new CallOutResponse();
        JsonObjectRequest request = WebRequest.request(url, object, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i(TAG, "onSuccess:" + response.toString());
                callOutResponse.status = 0;
                try {
                    Log.i(TAG, "respCode");
                    if (response.has("resp")) {
                        JSONObject resp = response.getJSONObject("resp");
                        if (resp.has("respCode")) {
                            callOutResponse.respCode = resp.getInt("respCode");
                            Log.i(TAG, "respCode:" + callOutResponse.respCode);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mDispatcher.dispatch(new CallOutAction(CallOutAction.CALL_OUT, new CallOut(callOutRequestBody.workNumber, callOutResponse)));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG, "onFailure:" + error.toString());
                callOutResponse.status = -1;
                mDispatcher.dispatch(new CallOutAction(CallOutAction.CALL_OUT, new CallOut("", callOutResponse)));
            }
        });
        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }

    public void getWorkNumber(Context context){
        Log.i(TAG, "getWorkNumber");
        String workNumber = context.getSharedPreferences("workNumber", Context.MODE_PRIVATE).getString("workNumber", "");
        Log.i(TAG, "workNumber:" + workNumber);
        mDispatcher.dispatch(new CallOutAction(CallOutAction.WORK_NUMBER, new CallOut(workNumber, null)));
    }

}
