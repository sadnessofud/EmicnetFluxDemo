package com.emicnet.emicallwebapi.voiceCode;

import android.util.Log;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.voiceCode.VoiceCodeResponse;

/**
 * Created by ShengWang on 2017/5/27.
 */

public class VoiceCodeStore extends Store {

    private static final String TAG = "VoiceCodeStore";
    private static VoiceCodeStore instance;

    private VoiceCodeResponse voiceCodeResponse = new VoiceCodeResponse();
    private String action;
    private int virifyCode;
    private String errorCode;

    protected VoiceCodeStore() {

    }

    public static VoiceCodeStore getInstance(){
        if (instance == null) {
            synchronized (VoiceCodeStore.class) {
                if (instance == null) {
                    instance = new VoiceCodeStore();
                }
            }
        }
        return instance;
    }

    public VoiceCodeResponse getVoiceCodeResponse() {
        return this.voiceCodeResponse;
    }

    @Override
    public StoreChangeEvent changeEvent() {
        Log.i(TAG,"changeEvent");
        return new StoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()) {
            case VoiceCodeAction.GET_VOICE_CODE:
                Log.i(TAG,"VoiceCodeAction.GET_VOICE_CODE");
                voiceCodeResponse = (VoiceCodeResponse)action.getData();
                break;
            default:
        }
        emitStoreChange();
    }


}
