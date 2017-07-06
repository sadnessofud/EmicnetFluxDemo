package com.emicnet.emicallwebapi.callCenterCallout.callOut.view;

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
import com.emicnet.emicallwebapi.callCenterCallout.callOut.CallOut;
import com.emicnet.emicallwebapi.callCenterCallout.callOut.CallOutAction;
import com.emicnet.emicallwebapi.callCenterCallout.callOut.CallOutActionsCreator;
import com.emicnet.emicallwebapi.callCenterCallout.callOut.CallOutStore;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.RESPONSE;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.callCenter.callOut.CallOutRequestBody;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class CallOutFragment extends Fragment implements View.OnClickListener{
    private TextView tv_header_back,tv_header_title;
    private EditText et_user_phone;
    private TextView tv_user_number;
    private RelativeLayout rl_call_out;
    private Dispatcher mDispatcher;
    private CallOutActionsCreator mActionsCreator;
    private CallOutStore mStore;


    public static CallOutFragment newInstance(){
        CallOutFragment instance = new CallOutFragment();
        return instance;
    }

    public static CallOutFragment newInstance(Bundle bundle){
        CallOutFragment instance = new CallOutFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_out_frag, container, false);
        initDependencies();
        setUpView(view);
        return view;
    }

    private void initData() {
        ProgressDialogUtils.get().showProgressDialog(getActivity(),getString(R.string.fetching_work_num));
        mActionsCreator.getWorkNumber(getActivity());
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mActionsCreator = CallOutActionsCreator.getInstance(mDispatcher);
        mStore = CallOutStore.getInstance();
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.create_user));
        tv_user_number = (TextView) view.findViewById(R.id.tv_user_number);
        et_user_phone = (EditText) view.findViewById(R.id.et_user_phone);
        rl_call_out = (RelativeLayout) view.findViewById(R.id.rl_call_out);
        rl_call_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_call_out:
                String workNumber = tv_user_number.getText().toString();
                String phone = et_user_phone.getText().toString();
                if (!TextUtils.isEmpty(phone)){
                    ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                    CallOutRequestBody body = new CallOutRequestBody(UserInfo.get().appId, workNumber, phone);
                    mActionsCreator.requestCallOut(getActivity(), body);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        mStore.register(this);
        mDispatcher.register(mStore);
        initData();
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
        CallOut callOut = mStore.getCallOut();
        switch (callOut.getActionType()){
            case CallOutAction.CALL_OUT:
                int status = callOut.getCallOutResponse().status;
                int respCode = callOut.getCallOutResponse().respCode;
                if (status == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(respCode)){
                    Toast.makeText(getActivity(), getString(R.string.call_out_success), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
                }
                break;
            case CallOutAction.WORK_NUMBER:
                String workNumber = callOut.getWorkNumber();
                if (TextUtils.isEmpty(workNumber)) {
                    Toast.makeText(getActivity(), getString(R.string.fetch_worknum_failed), Toast.LENGTH_SHORT).show();
                    tv_user_number.setText("");
                }else{
                    tv_user_number.setText(workNumber);
                }
                break;
        }

    }


}
