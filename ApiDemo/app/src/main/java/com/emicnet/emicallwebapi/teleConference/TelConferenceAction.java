package com.emicnet.emicallwebapi.teleConference;

import com.emicnet.emicallwebapi.fluxbase.Action;

/**
 * Created by ShengWang on 2017/6/13.
 */

public class TelConferenceAction extends Action{
    //type
    public static final String REQUEST_TEL_CONFERENCE = "REQUEST_TEL_CONFERENCE";
    public TelConferenceAction(String type, Object data) {
        super(type, data);
    }
}
