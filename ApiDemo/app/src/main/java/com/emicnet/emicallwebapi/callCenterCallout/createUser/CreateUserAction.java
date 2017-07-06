package com.emicnet.emicallwebapi.callCenterCallout.createUser;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.remote.bean.callCenter.createUser.CreateUserResponse;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class CreateUserAction extends Action<CreateUserResponse>{
    public static final String CREATE_USER = "CREATE_USER";
    public CreateUserAction(String type, CreateUserResponse data) {
        super(type, data);
    }
}
