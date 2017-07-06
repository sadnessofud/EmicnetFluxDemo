package com.emicnet.emicallwebapi.createNumberPair.matchNumber;

import android.util.Log;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.matchNumber.MatchNumberResponse;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class MatchNumberStore extends Store{
    private static final String TAG = "MatchNumberStore";
    private static MatchNumberStore instance;
    private MatchNumberResponse response = new MatchNumberResponse();

    private MatchNumberStore() {

    }

    public static MatchNumberStore getInstance(){
        if (instance == null) {
            synchronized (MatchNumberStore.class) {
                if (instance == null) {
                    instance = new MatchNumberStore();
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
            case MatchNumberAction.MATCH_NUMBER_PAIR:
                Log.i(TAG,"MatchNumberAction.MATCH_NUMBER_PAIR");
                response = (MatchNumberResponse) action.getData();
                break;
        }
        emitStoreChange();
    }

    public MatchNumberResponse getResponse() {
        return response;
    }
}
