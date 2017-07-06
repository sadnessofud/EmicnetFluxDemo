package com.emicnet.emicallwebapi.teleConference;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.teleConference.TelConferenceResponse;

/**
 * Created by ShengWang on 2017/6/13.
 */

public class TelConferenceStore extends Store{
    private static TelConferenceStore instance;
    private TelConferenceResponse response;
    private TelConferenceStore(){};

    public static TelConferenceStore getInstance (){
        if(instance == null){
            synchronized (TelConferenceStore.class){
                if (instance == null){
                   instance = new TelConferenceStore();
                }
            }
        }
        return  instance;
    }
    @Override
    public StoreChangeEvent changeEvent() {
        return new StoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()){
            case TelConferenceAction.REQUEST_TEL_CONFERENCE:
                response = (TelConferenceResponse) action.getData();
                break;
        }
        emitStoreChange();
    }

    public TelConferenceResponse getResponse(){
        return response;
    }
}
