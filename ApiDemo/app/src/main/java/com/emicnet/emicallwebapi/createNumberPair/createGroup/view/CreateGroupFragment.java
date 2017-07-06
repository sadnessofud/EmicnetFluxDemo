package com.emicnet.emicallwebapi.createNumberPair.createGroup.view;

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
import com.emicnet.emicallwebapi.createNumberPair.createGroup.CreateGroupActionCreator;
import com.emicnet.emicallwebapi.createNumberPair.createGroup.CreateGroupStore;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.RESPONSE;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.createGroup.CreateGroupRequestBody;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class CreateGroupFragment extends Fragment implements View.OnClickListener {
    private TextView tv_header_back,tv_header_title;
    private EditText et_group_name;
    private RelativeLayout rl_create_group;
    private Dispatcher mDispatcher;
    private CreateGroupStore mStore;
    private CreateGroupActionCreator mActionCreator;

    public CreateGroupFragment(){};

    public static CreateGroupFragment newInstance(){
        CreateGroupFragment instance = new CreateGroupFragment();
        return instance;
    }

    public static CreateGroupFragment newInstance(Bundle bundle){
        CreateGroupFragment instance = new CreateGroupFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_group_frag, container, false);
        initDependencies();
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.create_group));
        et_group_name = (EditText) view.findViewById(R.id.et_group_name);
        rl_create_group = (RelativeLayout) view.findViewById(R.id.rl_create_group);
        rl_create_group.setOnClickListener(this);
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mStore = CreateGroupStore.getInstance();
        mActionCreator = CreateGroupActionCreator.getInstance(mDispatcher);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_create_group:
                String groupName = et_group_name.getText().toString();
                if (!TextUtils.isEmpty(groupName)){
                    ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                    CreateGroupRequestBody requestBody = new CreateGroupRequestBody(UserInfo.get().appId, groupName);
                    mActionCreator.requestCreateGroup(getActivity(), requestBody);
                }
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mStore.register(this);
        mDispatcher.register(mStore);
    }

    @Override
    public void onPause() {
        super.onPause();
        mStore.unregister(this);
        mDispatcher.unregister(mStore);
    }

    @Subscribe
    public void onChangeEvent(Store.StoreChangeEvent event){
        ProgressDialogUtils.get().dismissDialog();
        int status = mStore.getResponse().status;
        int respCode = mStore.getResponse().respCode;
        if (status == RESPONSE.SUCCESS){
            Toast.makeText(getActivity(), getString(R.string.create_group_success), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
        }
    }
}
