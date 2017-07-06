package com.emicnet.emicallwebapi.callCenterCallout.createUser.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emicnet.emicallwebapi.R;
import com.emicnet.emicallwebapi.callCenterCallout.createUser.CreateUserActionCreator;
import com.emicnet.emicallwebapi.callCenterCallout.createUser.CreateUserStore;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.RESPONSE;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.callCenter.createUser.CreateuserRequestBody;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class CreateUserFragment extends Fragment implements View.OnClickListener {
    private TextView tv_header_back,tv_header_title;
    private EditText et_user_number,et_user_phone;
    private RelativeLayout rl_create;
    private Dispatcher mDispatcher;
    private CreateUserActionCreator mActionsCreator;
    private CreateUserStore mStore;

    public CreateUserFragment(){}

    public static CreateUserFragment newInstance(){
        CreateUserFragment instance = new CreateUserFragment();
        return instance;
    }

    public static CreateUserFragment newInstance(Bundle bundle){
        CreateUserFragment instance = new CreateUserFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_user_frag, container, false);
        initDependencies();
        setUpView(view);
        return view;
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mActionsCreator = CreateUserActionCreator.getInstance(mDispatcher);
        mStore = CreateUserStore.getInstance();
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.create_user));
        et_user_number = (EditText) view.findViewById(R.id.et_user_number);
        et_user_phone = (EditText) view.findViewById(R.id.et_user_phone);
        rl_create = (RelativeLayout) view.findViewById(R.id.rl_create);
        rl_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_create:
                String workNumber = et_user_number.getText().toString();
                String phone = et_user_phone.getText().toString();
                if (!TextUtils.isEmpty(phone)){
                    ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                    CreateuserRequestBody body = new CreateuserRequestBody(UserInfo.get().appId, workNumber, phone);
                    mActionsCreator.requestCreateUser(getActivity(), body);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        mStore.register(this);
        mDispatcher.register(mStore);
        super.onResume();
    }

    @Override
    public void onPause() {
        mStore.unregister(this);
        mDispatcher.unregister(mStore);
        super.onPause();
    }

    @Subscribe
    public void onChangeEvent(Store.StoreChangeEvent event){
        ProgressDialogUtils.get().dismissDialog();
        int status = mStore.getCreateUserResponse().status;
        int respCode = mStore.getCreateUserResponse().respCode;
        if (status == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(respCode)){
            Toast.makeText(getActivity(), getString(R.string.create_user), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
        }
    }
}
