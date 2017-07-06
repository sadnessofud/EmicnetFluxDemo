package com.emicnet.emicallwebapi.createSubAccount;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.main.EmicallWebApiApplication;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.createSubAccount.CreateSubAccountRequestBody;
import com.emicnet.emicallwebapi.remote.bean.createSubAccount.CreateSubAccountResponse;
import com.emicnet.emicallwebapi.remote.bean.querySubAccount.QuerySubAccountRequestBody;
import com.emicnet.emicallwebapi.remote.bean.querySubAccount.QuerySubAccountResponse;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class CreateSubAccountActionCreator {
    private final String TAG = "CreateSubAccountCreator";
    private static CreateSubAccountActionCreator instance;
    private Dispatcher mDispatcher;

    private CreateSubAccountActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static CreateSubAccountActionCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new CreateSubAccountActionCreator(dispatcher);
        }
        return instance;
    }

    public void requestCreateSubAccount(Context context, CreateSubAccountRequestBody createSubAccountRequestBody) {
        String url = new URI.Builder().buildRequestPath("Accounts")
                .buildSid(UserInfo.get().accountSid)
                .buildToken(UserInfo.get().authToken)
                .buildSigAndAuth()
                .buildFunction(Function.CREATE_SUBACCOUNT)
                .buildHttpUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", createSubAccountRequestBody.appId);
            if (!TextUtils.isEmpty(createSubAccountRequestBody.nickName)) {
                sub.put("nickName", createSubAccountRequestBody.nickName);
            }
            if (!TextUtils.isEmpty(createSubAccountRequestBody.mobile)) {
                sub.put("mobile", createSubAccountRequestBody.mobile);
            }
            if (!TextUtils.isEmpty(createSubAccountRequestBody.email)) {
                sub.put("email", createSubAccountRequestBody.email);
            }
            object.put("createSubAccount", sub);
            Log.i(TAG, "createSubAccountRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final CreateSubAccountResponse createSubAccountResponse = new CreateSubAccountResponse();
        JsonObjectRequest request = WebRequest.request(url, object, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i(TAG, "onSuccess:" + response.toString());
                createSubAccountResponse.status = 0;
                try {
                    Log.i(TAG, "respCode");
                    if (response.has("resp")) {
                        JSONObject resp = response.getJSONObject("resp");
                        if (resp.has("respCode")) {
                            createSubAccountResponse.respCode = resp.getInt("respCode");
                            Log.i(TAG, "respCode:" + createSubAccountResponse.respCode);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mDispatcher.dispatch(new CreateSubAccountAction(CreateSubAccountAction.CREATE_SUBACCOUNT, new CreateSubAccount(createSubAccountResponse)));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG, "onFailure:" + error.toString());
                createSubAccountResponse.status = -1;
                mDispatcher.dispatch(new CreateSubAccountAction(CreateSubAccountAction.CREATE_SUBACCOUNT, new CreateSubAccount(createSubAccountResponse)));
            }
        });


        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }

    public void requestQuerySubAccounts(Context context, QuerySubAccountRequestBody querySubAccountRequestBody) {
        String url = new URI.Builder().buildRequestPath("Accounts")
                .buildSid(UserInfo.get().accountSid)
                .buildToken(UserInfo.get().authToken)
                .buildSigAndAuth()
                .buildFunction(Function.QUERY_SUBACCOUNT)
                .buildHttpUrl();

        JSONObject object = new JSONObject();
        final JSONObject sub = new JSONObject();

        try {
            sub.put("appId", querySubAccountRequestBody.appId);
            object.put("subAccountList", sub);
            Log.i(TAG, "querySubAccountRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final QuerySubAccountResponse querySubAccountResponse = new QuerySubAccountResponse();
        JsonObjectRequest request = WebRequest.request(url, object, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i(TAG, "onSuccess:" + response.toString());
                querySubAccountResponse.status = 0;
                try {
                    Log.i(TAG, "respCode");
                    if (response.has("resp")) {
                        JSONObject resp = response.getJSONObject("resp");
                        if (resp.has("respCode")) {
                            querySubAccountResponse.respCode = resp.getInt("respCode");
                            Log.i(TAG, "respCode:" + querySubAccountResponse.respCode);
                            JSONObject subAccountList = resp.getJSONObject("subAccountList");
                            querySubAccountResponse.number = subAccountList.getInt("number");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mDispatcher.dispatch(new CreateSubAccountAction(CreateSubAccountAction.QUERY_SUBACCOUNT, new CreateSubAccount(querySubAccountResponse)));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG, "onFailure:" + error.toString());
                querySubAccountResponse.status = -1;
                mDispatcher.dispatch(new CreateSubAccountAction(CreateSubAccountAction.QUERY_SUBACCOUNT, new CreateSubAccount(querySubAccountResponse)));
            }
        });


        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);

    }

}
