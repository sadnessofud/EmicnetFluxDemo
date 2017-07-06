package com.emicnet.emicallwebapi.twoWayCall.view;

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
import com.emicnet.emicallwebapi.remote.bean.twoWayCall.TwoWayCallRequestBody;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.twoWayCall.TwoWayCallActionCreator;
import com.emicnet.emicallwebapi.twoWayCall.TwoWayCallStore;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/12.
 */

public class TwoWayCallFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_title, tv_back;
    private EditText et_phone1;
    private EditText et_phone2;
    private RelativeLayout rl_voice_receive;
    private Dispatcher mDispatcher;
    private TwoWayCallStore mStore;
    private TwoWayCallActionCreator mCreator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.twoway_call_frag, container, false);
        }
        initDependencies();
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_title.setText(R.string.twoway_call_title);
        tv_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_back.setOnClickListener(this);
        et_phone1 = (EditText) view.findViewById(R.id.et_phone1);
        et_phone2 = (EditText) view.findViewById(R.id.et_phone2);
        rl_voice_receive = (RelativeLayout) view.findViewById(R.id.rl_voice_receive);
        rl_voice_receive.setOnClickListener(this);
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mCreator = TwoWayCallActionCreator.getInstance(mDispatcher);
        mStore = TwoWayCallStore.getInstance();
    }

    public static TwoWayCallFragment newInstance(){
       TwoWayCallFragment instance = new TwoWayCallFragment();
        return instance;
    }

    public static TwoWayCallFragment newInstance(Bundle bundle){
        TwoWayCallFragment instance = new TwoWayCallFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_voice_receive:
                String from = et_phone1.getText().toString();
                String to = et_phone2.getText().toString();
                if (!TextUtils.isEmpty(from) && !TextUtils.isEmpty(to)){
                    TwoWayCallRequestBody requestBody = new TwoWayCallRequestBody(UserInfo.get().appId, from, to);
                    ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                    mCreator.requestTwoWayCall(getActivity(), requestBody);
                }
                break;
            case R.id.tv_header_back:
                getActivity().onBackPressed();
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
        if (status == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(respCode)){
            Toast.makeText(getActivity(), getString(R.string.call_out_success), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
        }
    }
}
