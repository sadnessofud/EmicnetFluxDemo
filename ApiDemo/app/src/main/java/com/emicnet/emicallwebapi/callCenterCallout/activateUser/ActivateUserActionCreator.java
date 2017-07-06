package com.emicnet.emicallwebapi.callCenterCallout.activateUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.main.EmicallWebApiApplication;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.callCenter.activateUser.ActivateUserRequestBody;
import com.emicnet.emicallwebapi.remote.bean.callCenter.activateUser.ActivateUserResponse;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class ActivateUserActionCreator {
    private final String TAG = "ActivateUserCreator";
    private static ActivateUserActionCreator instance;
    private Dispatcher mDispatcher;

    ActivateUserActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static ActivateUserActionCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActivateUserActionCreator(dispatcher);
        }
        return instance;
    }

    public void requestActivateUser(final Context context, final ActivateUserRequestBody activateUserRequestBody) {
        String url = new URI.Builder().buildRequestPath("SubAccounts")
                .buildSid(UserInfo.get().subAccountSid)
                .buildToken(UserInfo.get().subAccountToken)
                .buildSigAndAuth()
                .buildFunction(Function.SIGN_IN)
                .buildHttpUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", activateUserRequestBody.appId);
            if (!TextUtils.isEmpty(activateUserRequestBody.workNumber)) {
                sub.put("workNumber", activateUserRequestBody.workNumber);
            }
            if (!TextUtils.isEmpty(activateUserRequestBody.deviceNumber)) {
                sub.put("deviceNumber", activateUserRequestBody.deviceNumber);
            }
            object.put("signIn", sub);
            Log.i(TAG, "activateUserRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ActivateUserResponse activateUserResponse = new ActivateUserResponse();
        JsonObjectRequest request = WebRequest.request(url, object, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i(TAG, "onSuccess:" + response.toString());
                activateUserResponse.status = 0;
                try {
                    Log.i(TAG, "respCode");
                    if (response.has("resp")) {
                        JSONObject resp = response.getJSONObject("resp");
                        if (resp.has("respCode")) {
                            activateUserResponse.respCode = resp.getInt("respCode");
                            Log.i(TAG, "respCode:" + activateUserResponse.respCode);
                            if (WebUtils.isRequestRealSuc(activateUserResponse.respCode)) {
                                SharedPreferences.Editor editor = context.getSharedPreferences("workNumber", Context.MODE_PRIVATE).edit();
                                editor.putString("workNumber", activateUserRequestBody.workNumber).commit();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mDispatcher.dispatch(new ActivateUserAction(ActivateUserAction.ACTIVATE_USER, activateUserResponse));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG, "onFailure:" + error.toString());
                activateUserResponse.status = -1;
                mDispatcher.dispatch(new ActivateUserAction(ActivateUserAction.ACTIVATE_USER, activateUserResponse));
            }
        });


        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }

}
