package com.emicnet.emicallwebapi.callCenterCallout.activateUser;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.remote.bean.callCenter.activateUser.ActivateUserResponse;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class ActivateUserAction extends Action<ActivateUserResponse>{
    public static final String ACTIVATE_USER = "ACTIVATE_USER";
    public ActivateUserAction(String type, ActivateUserResponse data) {
        super(type, data);
    }
}
