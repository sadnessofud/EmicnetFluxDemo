package com.emicnet.emicallwebapi.createSubAccount;

import com.emicnet.emicallwebapi.remote.bean.createSubAccount.CreateSubAccountResponse;
import com.emicnet.emicallwebapi.remote.bean.querySubAccount.QuerySubAccountResponse;

/**
 * Created by ShengWang on 2017/6/28.
 */

public class CreateSubAccount {
    private String actionType;
    private CreateSubAccountResponse createSubAccountResponse;
    private QuerySubAccountResponse querySubAccountResponse;

    public CreateSubAccount() {
    }

    public CreateSubAccount(CreateSubAccountResponse createSubAccountResponse) {
        this.createSubAccountResponse = createSubAccountResponse;
    }

    public CreateSubAccount(QuerySubAccountResponse querySubAccountResponse) {
        this.querySubAccountResponse = querySubAccountResponse;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public CreateSubAccountResponse getCreateSubAccountResponse() {
        return createSubAccountResponse;
    }

    public void setCreateSubAccountResponse(CreateSubAccountResponse createSubAccountResponse) {
        this.createSubAccountResponse = createSubAccountResponse;
    }

    public QuerySubAccountResponse getQuerySubAccountResponse() {
        return querySubAccountResponse;
    }

    public void setQuerySubAccountResponse(QuerySubAccountResponse querySubAccountResponse) {
        this.querySubAccountResponse = querySubAccountResponse;
    }
}
