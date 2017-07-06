package com.emicnet.emicallwebapi.createNumberPair.createGroup;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.createGroup.CreateGroupResponse;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class CreateGroupAction extends Action<CreateGroupResponse>{
    //type
    public static final String CREATE_GROUP = "CREATE_GROUP";

    public CreateGroupAction(String type, CreateGroupResponse data) {
        super(type, data);
    }
}
