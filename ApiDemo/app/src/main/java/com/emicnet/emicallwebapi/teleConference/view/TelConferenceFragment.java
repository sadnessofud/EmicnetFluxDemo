package com.emicnet.emicallwebapi.teleConference.view;

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
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.RESPONSE;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.teleConference.TelConferenceRequestBody;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.teleConference.TelConferenceActionCreator;
import com.emicnet.emicallwebapi.teleConference.TelConferenceStore;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/13.
 */

public class TelConferenceFragment extends Fragment implements View.OnClickListener {
    private TextView tv_header_back,tv_header_title;
    private EditText et_phone1,et_phone2,et_phone3;
    private RelativeLayout rl_create_conference;
    private Dispatcher mDispatcher;
    private TelConferenceActionCreator mCreator;
    private TelConferenceStore mStore;

    public static TelConferenceFragment newInstance(){
        TelConferenceFragment instance = new TelConferenceFragment();
        return instance;
    }

    public static TelConferenceFragment newInstance(Bundle bundle){
        TelConferenceFragment instance = new TelConferenceFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.tel_conference_frag, container, false);
        initDependencies();
        setUpView(view);
        return view;
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mCreator = TelConferenceActionCreator.getInstance(mDispatcher);
        mStore = TelConferenceStore.getInstance();
    }

    private void setUpView(View view){
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.tel_conference));
        et_phone1 = (EditText) view.findViewById(R.id.et_phone1);
        et_phone2 = (EditText) view.findViewById(R.id.et_phone2);
        et_phone3 = (EditText) view.findViewById(R.id.et_phone3);
        rl_create_conference = (RelativeLayout) view.findViewById(R.id.rl_create_conference);
        rl_create_conference.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_create_conference:
                String from = et_phone1.getText().toString();
                String to1 = et_phone2.getText().toString();
                String to2 = et_phone3.getText().toString();
                String to = to1 + ", " + to2;
                if (!TextUtils.isEmpty(from) && !TextUtils.isEmpty(to)){
                    TelConferenceRequestBody requestBody = new TelConferenceRequestBody(UserInfo.get().appId, from, to);
                    ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                    mCreator.requestTwoWayCall(getActivity(), requestBody);
                }
                break;
        }
    }

    @Subscribe
    public void onChangeEvent(Store.StoreChangeEvent event){
        ProgressDialogUtils.get().dismissDialog();
        int status = mStore.getResponse().status;
        int respCode = mStore.getResponse().respCode;
        if (status == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(respCode)){
            Toast.makeText(getActivity(), getString(R.string.create_telconf_suc), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
        }
    }
}
