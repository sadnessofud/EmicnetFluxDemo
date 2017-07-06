package com.emicnet.emicallwebapi.voiceNotify.view;

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
import com.emicnet.emicallwebapi.remote.bean.uploadText.UploadTextRequestBody;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.emicnet.emicallwebapi.voiceNotify.VoiceNotify;
import com.emicnet.emicallwebapi.voiceNotify.VoiceNotifyAction;
import com.emicnet.emicallwebapi.voiceNotify.VoiceNotifyActionCreator;
import com.emicnet.emicallwebapi.voiceNotify.VoiceNotifyStore;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/14.
 */

public class VoiceNotifyFragment extends Fragment{
    private static VoiceNotifyFragment instance;
    private TextView tv_header_back, tv_header_title;
    private EditText et_phone_num;
    private EditText et_notify_text;
    private RelativeLayout rl_voice_receive;
    private VoiceNotifyActionCreator mCreator;
    private VoiceNotifyStore mStore;
    private Dispatcher mDispatcher;
    private View.OnClickListener OnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_header_back:
                    getActivity().onBackPressed();
                    break;
                case R.id.rl_voice_receive:
                    String notifyText = "";
                    if (et_notify_text.getText().toString().equals("")){
                        notifyText = et_notify_text.getHint().toString();
                    }else{
                        notifyText = et_notify_text.getText().toString();
                    }
                    String to;
                    to = et_phone_num.getText().toString();
                    if (!TextUtils.isEmpty(to) && !TextUtils.isEmpty(notifyText)){
                        ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                        UploadTextRequestBody uploadTextRequestBody = new UploadTextRequestBody(UserInfo.get().appId, notifyText, 1800);
                        mCreator.uploadText(getActivity(), uploadTextRequestBody, to);
                    }
                    break;
            }
        }
    };

    public VoiceNotifyFragment(){}

    public static VoiceNotifyFragment newInstance(){
        if (instance == null){
            synchronized (VoiceNotifyFragment.class){
                if (instance == null){
                    instance = new VoiceNotifyFragment();
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.voice_notify_frag, container, false);
        initDependencies();
        setUpView(view);
        return view;
    }

    private void initDependencies() {
        mStore = VoiceNotifyStore.getInstance();
        mDispatcher = Dispatcher.getInstance();
        mCreator = VoiceNotifyActionCreator.getInstance(mDispatcher);
    }
    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(OnClickListener);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(R.string.voice_notify);
        et_phone_num = (EditText) view.findViewById(R.id.et_phone_num);
        et_notify_text = (EditText) view.findViewById(R.id.et_notify_text);
        rl_voice_receive = (RelativeLayout) view.findViewById(R.id.rl_voice_receive);
        rl_voice_receive.setOnClickListener(OnClickListener);
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
        VoiceNotify voiceNotify = mStore.getVoiceNotify();
        switch (voiceNotify.getActionType()){
            case VoiceNotifyAction.UPLOAD_TEXT:
                int uploadStatus = voiceNotify.getUploadTextResponse().status;
                int respCode = voiceNotify.getUploadTextResponse().respCode;
                if (uploadStatus == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(respCode)){
                    Toast.makeText(getActivity(), getString(R.string.upload_success), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
                }
                break;
            case VoiceNotifyAction.REQUEST_VOICE_NOTIFY:
                int notifyStatus = voiceNotify.getVoiceNotifyResponse().status;
                int notifyCode = voiceNotify.getVoiceNotifyResponse().respCode;
                if (notifyStatus == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(notifyCode)){
                    Toast.makeText(getActivity(), getString(R.string.request_success), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), String.valueOf(notifyCode), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
