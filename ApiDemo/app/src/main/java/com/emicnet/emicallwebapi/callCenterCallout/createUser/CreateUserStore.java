package com.emicnet.emicallwebapi.callCenterCallout.createUser;

import android.util.Log;

import com.emicnet.emicallwebapi.fluxbase.Action;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.callCenter.createUser.CreateUserResponse;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class CreateUserStore extends Store{

        private static final String TAG = "CreateUserStore";
        private static CreateUserStore instance;

        private CreateUserResponse createUserResponse = new CreateUserResponse();

        protected CreateUserStore() {

        }

        public static CreateUserStore getInstance(){
            if (instance == null) {
                synchronized (CreateUserStore.class) {
                    if (instance == null) {
                        instance = new CreateUserStore();
                    }
                }
            }
            return instance;
        }

        public CreateUserResponse getCreateUserResponse() {
            return this.createUserResponse;
        }

        @Override
        public StoreChangeEvent changeEvent() {
            Log.i(TAG,"changeEvent");
            return new StoreChangeEvent();
        }

        @Override
        public void onAction(Action action) {
            switch (action.getType()) {
                case CreateUserAction.CREATE_USER:
                    Log.i(TAG,"VoiceCodeAction.GET_VOICE_CODE");
                    createUserResponse = (CreateUserResponse) action.getData();
                    break;
                default:
            }
            emitStoreChange();
        }


}
