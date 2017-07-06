package com.emicnet.emicallwebapi.createNumberPair.queryFreeNumbers;

import android.util.Log;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.queryFreeNumbers.QueryFreeNumbersResponse;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class QueryFreeNumbersStore extends Store{
    private static final String TAG = "QueryFreeNumbersStore";
    private static QueryFreeNumbersStore instance;
    private QueryFreeNumbersResponse response = new QueryFreeNumbersResponse();

    private QueryFreeNumbersStore() {

    }

    public static QueryFreeNumbersStore getInstance(){
        if (instance == null) {
            synchronized (QueryFreeNumbersStore.class) {
                if (instance == null) {
                    instance = new QueryFreeNumbersStore();
                }
            }
        }
        return instance;
    }

    @Override
    public StoreChangeEvent changeEvent() {
        Log.i(TAG,"changeEvent");
        return new StoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()){
            case QueryFreeNumbersAction.QUERY_FREE_NUMBERS:
                Log.i(TAG,"MatchNumberAction.MATCH_NUMBER_PAIR");
                response = (QueryFreeNumbersResponse) action.getData();
                break;
        }
        emitStoreChange();
    }

    public QueryFreeNumbersResponse getResponse() {
        return response;
    }

}
