package com.emicnet.emicallwebapi.createSubAccount;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.remote.bean.createSubAccount.CreateSubAccountResponse;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class CreateSubAccountAction extends Action<CreateSubAccount>{
    public static final String CREATE_SUBACCOUNT = "CREATE_SUBACCOUNT";
    public static final String QUERY_SUBACCOUNT = "QUERY_SUBACCOUNT";
    public CreateSubAccountAction(String type, CreateSubAccount data) {
        super(type, data);
    }
}
