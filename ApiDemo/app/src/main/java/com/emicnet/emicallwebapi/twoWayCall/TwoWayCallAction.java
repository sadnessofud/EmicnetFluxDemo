package com.emicnet.emicallwebapi.twoWayCall;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.remote.bean.twoWayCall.TwoWayCallResponse;

/**
 * Created by ShengWang on 2017/6/12.
 */

public class TwoWayCallAction extends Action<TwoWayCallResponse> {
    //type
    public static final String START_TWOWAY_CALL = "START_TWOWAY_CALL";
    public TwoWayCallAction(String type, TwoWayCallResponse data) {
        super(type, data);
    }
}
