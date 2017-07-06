package com.emicnet.emicallwebapi.voiceNotify;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.remote.bean.voiceNotify.VoiceNotifyResponse;

/**
 * Created by ShengWang on 2017/6/14.
 */

public class VoiceNotifyAction extends Action<VoiceNotify> {
    //type
    public static final String REQUEST_VOICE_NOTIFY = "REQUEST_VOICE_NOTIFY";
    public static final String UPLOAD_TEXT = "UPLOAD_TEXT";

    public VoiceNotifyAction(String type, VoiceNotify data) {
        super(type, data);
    }

}
