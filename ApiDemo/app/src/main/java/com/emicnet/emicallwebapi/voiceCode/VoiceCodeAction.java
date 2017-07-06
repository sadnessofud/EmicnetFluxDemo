package com.emicnet.emicallwebapi.voiceCode;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.remote.bean.voiceCode.VoiceCodeResponse;

/**
 * Created by charlie on 2017/6/8.
 */

public class VoiceCodeAction extends Action<VoiceCodeResponse>{

    //type
    public static final String GET_VOICE_CODE = "GET_VOICE_CODE";
    public VoiceCodeAction(String type,VoiceCodeResponse voiceCodeResponse){
        super(type, voiceCodeResponse);
    }

}
