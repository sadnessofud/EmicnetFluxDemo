package com.emicnet.emicallwebapi.callCenterCallout.createUser;

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
import com.emicnet.emicallwebapi.remote.bean.callCenter.createUser.CreateUserResponse;
import com.emicnet.emicallwebapi.remote.bean.callCenter.createUser.CreateuserRequestBody;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class CreateUserActionCreator {
    private final String TAG = "CreateUserActionCreator";
    private static CreateUserActionCreator instance;
    private Dispatcher mDispatcher;

    CreateUserActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static CreateUserActionCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new CreateUserActionCreator(dispatcher);
        }
        return instance;
    }

    public void requestCreateUser(Context context, CreateuserRequestBody createuserRequestBody){
        String url = new URI.Builder().buildRequestPath("SubAccounts")
                .buildSid(UserInfo.get().subAccountSid)
                .buildToken(UserInfo.get().subAccountToken)
                .buildSigAndAuth()
                .buildFunction(Function.CREATE_USER)
                .buildHttpUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", createuserRequestBody.appId);
            if (!TextUtils.isEmpty(createuserRequestBody.workNumber)){
                sub.put("workNumber", createuserRequestBody.workNumber);
            }
            if (!TextUtils.isEmpty(createuserRequestBody.phone)){
                sub.put("phone", createuserRequestBody.phone);
            }
            object.put("createUser", sub);
            Log.i(TAG, "createuserRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final CreateUserResponse createUserResponse = new CreateUserResponse();
        JsonObjectRequest request = WebRequest.request(url, object, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i(TAG,"onSuccess:"+response.toString());
                createUserResponse.status = 0;
                try {
                    if(response !=null){
                        Log.i(TAG,"respCode");
                        if(response.has("resp")){
                            JSONObject resp = response.getJSONObject("resp");
                            if(resp.has("respCode")){
                                createUserResponse.respCode = resp.getInt("respCode");
                                Log.i(TAG,"respCode:"+ createUserResponse.respCode );
                            }
                        }

                    }

                }catch  (JSONException e) {
                    e.printStackTrace();
                }

                mDispatcher.dispatch(new CreateUserAction(CreateUserAction.CREATE_USER,createUserResponse));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG,"onFailure:"+error.toString());
                createUserResponse.status = -1;
                mDispatcher.dispatch(new CreateUserAction(CreateUserAction.CREATE_USER,createUserResponse));
            }
        });


        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }

}
