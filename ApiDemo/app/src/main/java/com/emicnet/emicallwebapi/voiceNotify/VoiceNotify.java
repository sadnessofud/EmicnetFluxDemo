package com.emicnet.emicallwebapi.voiceNotify;

import com.emicnet.emicallwebapi.remote.bean.uploadText.UploadTextResponse;
import com.emicnet.emicallwebapi.remote.bean.voiceNotify.VoiceNotifyResponse;

/**
 * Created by ShengWang on 2017/6/28.
 */

public class VoiceNotify {
    private String actionType;
    private VoiceNotifyResponse voiceNotifyResponse;
    private UploadTextResponse uploadTextResponse;

    public VoiceNotify() {
    }

    public VoiceNotify(VoiceNotifyResponse voiceNotifyResponse) {
        this.voiceNotifyResponse = voiceNotifyResponse;
    }

    public VoiceNotify(UploadTextResponse uploadTextResponse) {
        this.uploadTextResponse = uploadTextResponse;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public VoiceNotifyResponse getVoiceNotifyResponse() {
        return voiceNotifyResponse;
    }

    public void setVoiceNotifyResponse(VoiceNotifyResponse voiceNotifyResponse) {
        this.voiceNotifyResponse = voiceNotifyResponse;
    }

    public UploadTextResponse getUploadTextResponse() {
        return uploadTextResponse;
    }

    public void setUploadTextResponse(UploadTextResponse uploadTextResponse) {
        this.uploadTextResponse = uploadTextResponse;
    }
}
