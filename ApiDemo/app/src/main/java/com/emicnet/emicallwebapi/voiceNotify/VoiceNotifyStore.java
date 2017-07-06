package com.emicnet.emicallwebapi.voiceNotify;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;

/**
 * Created by ShengWang on 2017/6/14.
 */

public class VoiceNotifyStore extends Store{
    private static final String TAG = "VoiceNotifyStore";
    private static VoiceNotifyStore instance;
    private VoiceNotify voiceNotify;

    protected VoiceNotifyStore() {

    }

    public static VoiceNotifyStore getInstance(){
        if (instance == null) {
            synchronized (VoiceNotifyStore.class) {
                if (instance == null) {
                    instance = new VoiceNotifyStore();
                }
            }
        }
        return instance;
    }
    @Override
    public StoreChangeEvent changeEvent() {
        return new StoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()){
            case VoiceNotifyAction.REQUEST_VOICE_NOTIFY:
                voiceNotify = (VoiceNotify) action.getData();
                voiceNotify.setActionType(action.getType());
                emitStoreChange();
                break;
            case VoiceNotifyAction.UPLOAD_TEXT:
                voiceNotify = (VoiceNotify) action.getData();
                voiceNotify.setActionType(action.getType());
                emitStoreChange();
                break;
        }
    }

    public VoiceNotify getVoiceNotify() {
        return voiceNotify;
    }
}
