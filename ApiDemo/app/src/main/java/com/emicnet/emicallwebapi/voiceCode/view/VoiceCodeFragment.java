package com.emicnet.emicallwebapi.voiceCode.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.emicnet.emicallwebapi.remote.bean.voiceCode.VoiceCodeRequestBody;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.emicnet.emicallwebapi.utils.VerifyCodeUtils;
import com.emicnet.emicallwebapi.voiceCode.VoiceCodeActionCreator;
import com.emicnet.emicallwebapi.voiceCode.VoiceCodeStore;
import com.emicnet.emicallwebapi.voiceNotify.VoiceNotify;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/5/27.
 */

public class VoiceCodeFragment extends Fragment {

    private Dispatcher mDispatcher;
    private VoiceCodeActionCreator mActionsCreator;
    private VoiceCodeStore mStore;
    private TextView tv_header_back;
    private TextView tv_header_title;
    private RelativeLayout rl_voice_receive;
    private EditText et_phone_num,et_voice_code;
    private RelativeLayout rl_verify;
    private int verifyCode = 0;
    private View.OnClickListener OnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_header_back:
                    getActivity().onBackPressed();
                    break;
                case R.id.rl_voice_receive:
                    verifyCode = VerifyCodeUtils.getVerifyCode();
                    if (!et_phone_num.getText().toString().equals("")){
                        VoiceCodeRequestBody body = new VoiceCodeRequestBody(UserInfo.get().appId, verifyCode, et_phone_num.getText().toString());
                        ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                        mActionsCreator.requestVoiceCode(getActivity(), body);
                    }
                    break;
                case R.id.rl_verify:
                    if (et_voice_code.getText().toString().equals(String.valueOf(verifyCode))){
                        Toast.makeText(getActivity(), R.string.verify_success, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), R.string.verify_failed, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public VoiceCodeFragment(){};

    public static VoiceCodeFragment newInstance(){
        VoiceCodeFragment instance = new VoiceCodeFragment();
        return instance;
    }

    public static VoiceCodeFragment newInstance(Bundle bundle){
        VoiceCodeFragment instance = new VoiceCodeFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (view == null){
            view = inflater.inflate(R.layout.voicecode_frag, container, false);
        }
        initDependencies();
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(OnClickListener);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(R.string.voice_code_title);
        rl_voice_receive = (RelativeLayout) view.findViewById(R.id.rl_voice_receive);
        rl_voice_receive.setOnClickListener(OnClickListener);
        et_phone_num = (EditText) view.findViewById(R.id.et_phone_num);
        et_voice_code = (EditText) view.findViewById(R.id.et_voice_code);
        rl_verify = (RelativeLayout) view.findViewById(R.id.rl_verify);
        rl_verify.setOnClickListener(OnClickListener);
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mActionsCreator = VoiceCodeActionCreator.getInstance(mDispatcher);
        mStore = VoiceCodeStore.getInstance();
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
        int status = mStore.getVoiceCodeResponse().status;
        int respCode = mStore.getVoiceCodeResponse().respCode;
        if (status == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(respCode)){
            Toast.makeText(getActivity(), getString(R.string.get_verifycode_success), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
        }
    }

}
