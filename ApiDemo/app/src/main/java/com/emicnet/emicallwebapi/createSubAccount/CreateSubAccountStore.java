package com.emicnet.emicallwebapi.createSubAccount;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class CreateSubAccountStore extends Store{
    private static CreateSubAccountStore instance;
    private CreateSubAccount createSubAccount;

    private CreateSubAccountStore(){}

    public static CreateSubAccountStore getInstance(){
        if (instance == null){
            synchronized (CreateSubAccountStore.class){
                if (instance == null){
                    instance = new CreateSubAccountStore();
                }
            }
        }
        return instance;
    }


    @Override
    public StoreChangeEvent changeEvent() {
        return new StoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()){
            case CreateSubAccountAction.CREATE_SUBACCOUNT:
                createSubAccount = (CreateSubAccount) action.getData();
                createSubAccount.setActionType(action.getType());
                emitStoreChange();
                break;
            case CreateSubAccountAction.QUERY_SUBACCOUNT:
                createSubAccount = (CreateSubAccount) action.getData();
                createSubAccount.setActionType(action.getType());
                emitStoreChange();
                break;
        }
    }

    public CreateSubAccount getCreateSubAccount() {
        return createSubAccount;
    }
}
