package com.emicnet.emicallwebapi.createNumberPair.matchNumber.view;

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
import com.emicnet.emicallwebapi.createNumberPair.matchNumber.MatchNumberActionCreator;
import com.emicnet.emicallwebapi.createNumberPair.matchNumber.MatchNumberStore;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.RESPONSE;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.matchNumber.MatchNumberRequestBody;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class MatchNumberFragment extends Fragment implements View.OnClickListener {
    private TextView tv_header_back,tv_header_title;
    private RelativeLayout rl_match_number;
    private EditText et_seller_phone,et_buyer_phone;
    private MatchNumberStore mStore;
    private Dispatcher mDispatcher;
    private MatchNumberActionCreator mCreator;

    public static MatchNumberFragment newInstance(){
        MatchNumberFragment instance = new MatchNumberFragment();
        return instance;
    }

    public static MatchNumberFragment newInstance(Bundle bundle){
        MatchNumberFragment instance = new MatchNumberFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.match_number_frag, container, false);
        initDependencies();
        setUpView(view);
        return view;
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mCreator = MatchNumberActionCreator.getInstance(mDispatcher);
        mStore = MatchNumberStore.getInstance();
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.match_number));
        et_seller_phone = (EditText) view.findViewById(R.id.et_seller_phone);
        et_buyer_phone = (EditText) view.findViewById(R.id.et_buyer_phone);
        rl_match_number = (RelativeLayout) view.findViewById(R.id.rl_match_number);
        rl_match_number.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_match_number:
                String sellerNumber = et_seller_phone.getText().toString();
                String buyerNumber = et_buyer_phone.getText().toString();
                if (!TextUtils.isEmpty(sellerNumber) && !TextUtils.isEmpty(buyerNumber)){
                    ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                    MatchNumberRequestBody requestBody = new MatchNumberRequestBody(UserInfo.get().appId, sellerNumber, buyerNumber);
                    mCreator.requestCreateNumberPair(getActivity(), requestBody);
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
            Toast.makeText(getActivity(), getString(R.string.match_num_success), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
        }
    }
}
