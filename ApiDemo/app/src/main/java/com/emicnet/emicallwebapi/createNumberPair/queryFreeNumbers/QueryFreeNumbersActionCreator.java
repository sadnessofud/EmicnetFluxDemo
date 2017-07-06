package com.emicnet.emicallwebapi.createNumberPair.queryFreeNumbers;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.main.EmicallWebApiApplication;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.remote.bean.URI;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.queryFreeNumbers.QueryFreeNumbersRequestBody;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.queryFreeNumbers.QueryFreeNumbersResponse;
import com.emicnet.emicallwebapi.remote.model.Callback;
import com.emicnet.emicallwebapi.remote.webservice.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class QueryFreeNumbersActionCreator {
    private final String TAG = "VoiceCodeActionCreator";
    private static QueryFreeNumbersActionCreator instance;
    private Dispatcher mDispatcher;

    QueryFreeNumbersActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public static QueryFreeNumbersActionCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new QueryFreeNumbersActionCreator(dispatcher);
        }
        return instance;
    }

    public void requestQueryFreeNumbers(Context context, QueryFreeNumbersRequestBody queryFreeNumbersRequestBody){
        String url = new URI.Builder().buildRequestPath("SubAccounts")
                .buildSid(UserInfo.get().subAccountSid)
                .buildToken(UserInfo.get().subAccountToken)
                .buildSigAndAuth()
                .buildFunction(Function.QUERY_FREE_NUMBERS)
                .buildHttpUrl();

        JSONObject object = new JSONObject();
        JSONObject sub = new JSONObject();

        try {
            sub.put("appId", queryFreeNumbersRequestBody.appId);
            object.put("freeNumbers", sub);
            Log.i(TAG, "queryFreeNumbersRequestBody:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final QueryFreeNumbersResponse queryFreeNumbersResponse = new QueryFreeNumbersResponse();
        JsonObjectRequest request = WebRequest.request(url, object, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.i(TAG,"onSuccess:"+response.toString());
                queryFreeNumbersResponse.status = 0;
                try {
                    if(response !=null){
                        Log.i(TAG,"respCode");
                        if(response.has("resp")){
                            JSONObject resp = response.getJSONObject("resp");
                            if(resp.has("respCode")){
                                queryFreeNumbersResponse.respCode = resp.getInt("respCode");
                                Log.i(TAG,"respCode:"+ queryFreeNumbersResponse.respCode );
                                JSONObject freeNumbers = resp.getJSONObject("freeNumbers");
                                JSONArray numbers = freeNumbers.getJSONArray("numbers");
                                queryFreeNumbersResponse.numbers = numbers.toString();
                                Log.i(TAG, "numbers:" + numbers);
                            }
                        }

                    }

                }catch  (JSONException e) {
                    e.printStackTrace();
                }

                mDispatcher.dispatch(new QueryFreeNumbersAction(QueryFreeNumbersAction.QUERY_FREE_NUMBERS,queryFreeNumbersResponse));

            }

            @Override
            public void onFailure(VolleyError error) {
                Log.i(TAG,"onFailure:"+error.toString());
                queryFreeNumbersResponse.status = -1;
                mDispatcher.dispatch(new QueryFreeNumbersAction(QueryFreeNumbersAction.QUERY_FREE_NUMBERS,queryFreeNumbersResponse));
            }
        });


        EmicallWebApiApplication app = (EmicallWebApiApplication) context.getApplicationContext();
        app.addToRequestQueue(request);
    }

}
