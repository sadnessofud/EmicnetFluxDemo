package com.emicnet.emicallwebapi.twoWayCall;


import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.twoWayCall.TwoWayCallResponse;

/**
 * Created by ShengWang on 2017/6/12.
 */

public class TwoWayCallStore extends Store {
    private static TwoWayCallStore instance;
    private TwoWayCallResponse response;
    private TwoWayCallStore(){}
    @Override
    public StoreChangeEvent changeEvent() {
        return new StoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()){
            case TwoWayCallAction.START_TWOWAY_CALL:
                response = (TwoWayCallResponse) action.getData();
                break;
        }
        emitStoreChange();
    }

    public static TwoWayCallStore getInstance(){
        if (instance == null) {
            synchronized (TwoWayCallStore.class) {
                if (instance == null) {
                    instance = new TwoWayCallStore();
                }
            }
        }
        return instance;
    }

    public TwoWayCallResponse getResponse() {
        return response;
    }
}
