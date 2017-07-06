package com.emicnet.emicallwebapi.callCenterCallout.activateUser;

import android.util.Log;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.callCenter.activateUser.ActivateUserResponse;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class ActivateUserStore extends Store{
    private static final String TAG = "ActivateUserStore";
    private static ActivateUserStore instance;

    private ActivateUserResponse activateUserResponse = new ActivateUserResponse();

    protected ActivateUserStore() {

    }

    public static ActivateUserStore getInstance(){
        if (instance == null) {
            synchronized (ActivateUserStore.class) {
                if (instance == null) {
                    instance = new ActivateUserStore();
                }
            }
        }
        return instance;
    }

    public ActivateUserResponse getActivateUserResponse() {
        return activateUserResponse;
    }

    @Override
    public StoreChangeEvent changeEvent() {
        Log.i(TAG,"changeEvent");
        return new StoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()) {
            case ActivateUserAction.ACTIVATE_USER:
                Log.i(TAG,"VoiceCodeAction.GET_VOICE_CODE");
                activateUserResponse = (ActivateUserResponse) action.getData();
                break;
            default:
        }
        emitStoreChange();
    }



}
