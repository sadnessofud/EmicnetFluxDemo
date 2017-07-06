package com.emicnet.emicallwebapi.callCenterCallout.callOut;

import android.util.Log;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;

/**
 * Created by ShengWang on 2017/6/27.
 */

public class CallOutStore extends Store{
    private static final String TAG = "CallOutStore";
    private static CallOutStore instance;
    private CallOut callOut;

    protected CallOutStore() {
    }

    public static CallOutStore getInstance(){
        if (instance == null) {
            synchronized (CallOutStore.class) {
                if (instance == null) {
                    instance = new CallOutStore();
                }
            }
        }
        return instance;
    }


    @Override
    public Store.StoreChangeEvent changeEvent() {
        Log.i(TAG,"changeEvent");
        return new Store.StoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        Log.i(TAG, "onAction");
        switch (action.getType()) {
            case CallOutAction.CALL_OUT:
                Log.i(TAG,"CallOutAction.CALL_OUT");
                callOut = (CallOut) action.getData();
                callOut.setActionType(action.getType());
                emitStoreChange();
                break;
            case CallOutAction.WORK_NUMBER:
                Log.i(TAG, "CallOutAction.WORK_NUMBER");
                callOut = (CallOut) action.getData();
                callOut.setActionType(action.getType());
                emitStoreChange();
                break;
            default:
        }
    }

    public CallOut getCallOut() {
        return callOut;
    }
}
