package com.emicnet.emicallwebapi.createSubAccount.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emicnet.emicallwebapi.R;
import com.emicnet.emicallwebapi.createSubAccount.CreateSubAccountAction;
import com.emicnet.emicallwebapi.createSubAccount.CreateSubAccountActionCreator;
import com.emicnet.emicallwebapi.createSubAccount.CreateSubAccountStore;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.RESPONSE;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.createSubAccount.CreateSubAccountRequestBody;
import com.emicnet.emicallwebapi.remote.bean.querySubAccount.QuerySubAccountRequestBody;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class CreateSubAccountFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "CreateSubAccountFrag";
    private TextView tv_header_back,tv_header_title;
    private EditText et_account_name,et_phone,et_email;
    private RelativeLayout rl_create_subaccount,rl_query_subaccounts;
    private TextView tv_show_subaccounts;
    private CreateSubAccountStore mStore;
    private CreateSubAccountActionCreator mActionCreator;
    private Dispatcher mDispatcher;

    public static CreateSubAccountFragment newInstance(){
        return new CreateSubAccountFragment();
    }

    public static CreateSubAccountFragment newInstance(Bundle bundle){
        CreateSubAccountFragment instance = new CreateSubAccountFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_subccount_frag, container, false);
        initDependencies();
        setUpView(view);
        return view;
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mStore = CreateSubAccountStore.getInstance();
        mActionCreator = CreateSubAccountActionCreator.getInstance(mDispatcher);
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.create_subaccount));
        et_account_name = (EditText) view.findViewById(R.id.et_account_name);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_email = (EditText) view.findViewById(R.id.et_email);
        rl_create_subaccount = (RelativeLayout) view.findViewById(R.id.rl_create_subaccount);
        rl_create_subaccount.setOnClickListener(this);
        rl_query_subaccounts = (RelativeLayout) view.findViewById(R.id.rl_query_subaccounts);
        rl_query_subaccounts.setOnClickListener(this);
        tv_show_subaccounts = (TextView) view.findViewById(R.id.tv_show_subaccounts);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_create_subaccount:
                String nickName = et_account_name.getText().toString();
                String phone = et_phone.getText().toString();
                String email = et_email.getText().toString();
                ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                CreateSubAccountRequestBody requestBody = new CreateSubAccountRequestBody(UserInfo.get().appId, nickName, phone, email);
                mActionCreator.requestCreateSubAccount(getActivity(), requestBody);
                break;
            case R.id.rl_query_subaccounts:
                ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                QuerySubAccountRequestBody querySubAccountRequestBody = new QuerySubAccountRequestBody(UserInfo.get().appId);
                mActionCreator.requestQuerySubAccounts(getActivity(), querySubAccountRequestBody);
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
        Log.i(TAG, "onChangeEvent");
        ProgressDialogUtils.get().dismissDialog();
        String actionType = mStore.getCreateSubAccount().getActionType();
        switch (actionType){
            case CreateSubAccountAction.CREATE_SUBACCOUNT:
                int status = mStore.getCreateSubAccount().getCreateSubAccountResponse().status;
                int createCode = mStore.getCreateSubAccount().getCreateSubAccountResponse().respCode;
                if (status == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(createCode)){
                    Toast.makeText(getActivity(), getString(R.string.create_sub_account_suc), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), String.valueOf(createCode), Toast.LENGTH_SHORT).show();
                }
                break;
            case CreateSubAccountAction.QUERY_SUBACCOUNT:
                int queryStatus = mStore.getCreateSubAccount().getQuerySubAccountResponse().status;
                int queryCode = mStore.getCreateSubAccount().getQuerySubAccountResponse().respCode;
                if (queryStatus == RESPONSE.SUCCESS && WebUtils.isRequestRealSuc(queryCode)){
                    Toast.makeText(getActivity(), getString(R.string.query_sub_account_suc), Toast.LENGTH_SHORT).show();
                    tv_show_subaccounts.setText(mStore.getCreateSubAccount().getQuerySubAccountResponse().number + "");
                }else{
                    Toast.makeText(getActivity(), String.valueOf(queryCode), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

}
