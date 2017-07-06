package com.emicnet.emicallwebapi.callCenterCallout.activateUser.view;

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
import com.emicnet.emicallwebapi.callCenterCallout.activateUser.ActivateUserActionCreator;
import com.emicnet.emicallwebapi.callCenterCallout.activateUser.ActivateUserStore;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.RESPONSE;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.remote.bean.callCenter.activateUser.ActivateUserRequestBody;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class ActivateUserFragment extends Fragment implements View.OnClickListener {
    private TextView tv_header_back, tv_header_title;
    private EditText et_user_number, et_user_phone;
    private RelativeLayout rl_activate_user;
    private Dispatcher mDispatcher;
    private ActivateUserActionCreator mActionsCreator;
    private ActivateUserStore mStore;

    public ActivateUserFragment() {
    }

    public static ActivateUserFragment newInstance() {
        ActivateUserFragment instance = new ActivateUserFragment();
        return instance;
    }

    public static ActivateUserFragment newInstance(Bundle bundle) {
        ActivateUserFragment instance = new ActivateUserFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activate_user_frag, container, false);
        initDependencies();
        setUpView(view);
        return view;
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mActionsCreator = ActivateUserActionCreator.getInstance(mDispatcher);
        mStore = ActivateUserStore.getInstance();
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.activate_user));
        et_user_number = (EditText) view.findViewById(R.id.et_user_number);
        et_user_phone = (EditText) view.findViewById(R.id.et_user_phone);
        rl_activate_user = (RelativeLayout) view.findViewById(R.id.rl_activate_user);
        rl_activate_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_activate_user:
                String workNumber = et_user_number.getText().toString();
                String phone = et_user_phone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                    ActivateUserRequestBody body = new ActivateUserRequestBody(UserInfo.get().appId, workNumber, phone);
                    mActionsCreator.requestActivateUser(getActivity(), body);
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
    public void onChangeEvent(Store.StoreChangeEvent event) {
        ProgressDialogUtils.get().dismissDialog();
        int status = mStore.getActivateUserResponse().status;
        int respCode = mStore.getActivateUserResponse().respCode;
        if (status == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(respCode)) {
            Toast.makeText(getActivity(), getString(R.string.activate_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
        }
    }

}
