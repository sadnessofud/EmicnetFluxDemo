package com.emicnet.emicallwebapi.createNumberPair.createGroup;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.main.EmicallWebApiApplication;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.createGroup.CreateGroupRequestBody;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.createGroup.CreateGroupResponse;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class CreateGroupActionCreator {
    private final String TAG = "CreateGroupCreator";
    private static CreateGroupActionCreator instance;
    private Dispatcher mDispatcher;

    CreateGroupActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static CreateGroupActionCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new CreateGroupActionCreator(dispatcher);
        }
        return instance;
    }

    public void requestCreateGroup(Context context, CreateGroupRequestBody createGroupRequestBody){
        String url = new URI.Builder().buildRequestPath("SubAccounts")
                .buildSid(UserInfo.get().subAccountSid)
                .buildToken(UserInfo.get().subAccountToken)
                .buildSigAndAuth()
                .buildFunction(Function.CREATE_GROUP)
                .buildHttpUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", createGroupRequestBody.appId);
            sub.put("groupName", createGroupRequestBody.groupName);
            object.put("createGroup", sub);
            Log.i(TAG, "createGroupRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final CreateGroupResponse voiceCodeResponse = new CreateGroupResponse();
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

                mDispatcher.dispatch(new CreateGroupAction(CreateGroupAction.CREATE_GROUP,voiceCodeResponse));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG,"onFailure:"+error.toString());
                voiceCodeResponse.status = -1;
                mDispatcher.dispatch(new CreateGroupAction(CreateGroupAction.CREATE_GROUP,voiceCodeResponse));
            }
        });


        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }

}
