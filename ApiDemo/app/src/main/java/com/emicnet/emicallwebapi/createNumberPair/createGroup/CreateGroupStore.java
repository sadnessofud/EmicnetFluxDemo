package com.emicnet.emicallwebapi.createNumberPair.createGroup;

import android.util.Log;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.createGroup.CreateGroupResponse;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class CreateGroupStore extends Store{
    private static final String TAG = "CreateGroupStore";
    private static CreateGroupStore instance;
    private CreateGroupResponse response = new CreateGroupResponse();

    private CreateGroupStore() {

    }

    public static CreateGroupStore getInstance(){
        if (instance == null) {
            synchronized (CreateGroupStore.class) {
                if (instance == null) {
                    instance = new CreateGroupStore();
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
            case CreateGroupAction.CREATE_GROUP:
                Log.i(TAG,"MatchNumberAction.MATCH_NUMBER_PAIR");
                response = (CreateGroupResponse) action.getData();
                break;
        }
        emitStoreChange();
    }

    public CreateGroupResponse getResponse() {
        return response;
    }
}
