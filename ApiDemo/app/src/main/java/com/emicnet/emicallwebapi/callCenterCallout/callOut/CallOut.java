package com.emicnet.emicallwebapi.callCenterCallout.callOut;

import com.emicnet.emicallwebapi.remote.bean.callCenter.callOut.CallOutResponse;

/**
 * Created by ShengWang on 2017/6/28.
 */

public class CallOut {
    private String actionType;
    private String workNumber;
    private CallOutResponse callOutResponse;

    public CallOut() {
    }

    public CallOut(String workNumber, CallOutResponse callOutResponse) {
        this.workNumber = workNumber;
        this.callOutResponse = callOutResponse;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public CallOutResponse getCallOutResponse() {
        return callOutResponse;
    }

    public void setCallOutResponse(CallOutResponse callOutResponse) {
        this.callOutResponse = callOutResponse;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
