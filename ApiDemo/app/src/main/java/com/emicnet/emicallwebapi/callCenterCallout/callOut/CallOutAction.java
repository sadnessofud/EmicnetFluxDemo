package com.emicnet.emicallwebapi.callCenterCallout.callOut;

import com.emicnet.emicallwebapi.fluxbase.Action;

/**
 * Created by ShengWang on 2017/6/27.
 */

public class CallOutAction extends Action<CallOut>{
    public static final String CALL_OUT = "CALL_OUT";
    public static final String WORK_NUMBER = "WORK_NUMBER";
    public CallOutAction(String type, CallOut data) {
        super(type, data);
    }
}
