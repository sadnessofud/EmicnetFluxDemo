package com.emicnet.emicallwebapi.createNumberPair.queryFreeNumbers.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emicnet.emicallwebapi.R;
import com.emicnet.emicallwebapi.createNumberPair.queryFreeNumbers.QueryFreeNumbersActionCreator;
import com.emicnet.emicallwebapi.fluxbase.Dispatcher;
import com.emicnet.emicallwebapi.fluxbase.Store;
import com.emicnet.emicallwebapi.remote.bean.RESPONSE;
import com.emicnet.emicallwebapi.remote.bean.UserInfo;
import com.emicnet.emicallwebapi.remote.bean.createNumberPair.queryFreeNumbers.QueryFreeNumbersRequestBody;
import com.emicnet.emicallwebapi.createNumberPair.queryFreeNumbers.QueryFreeNumbersStore;
import com.emicnet.emicallwebapi.remote.utils.WebUtils;
import com.emicnet.emicallwebapi.utils.ProgressDialogUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class QueryFreeNumbersFragment extends Fragment implements View.OnClickListener {
    private TextView tv_header_back,tv_header_title;
    private RelativeLayout rl_query_free_numbers;
    private TextView tv_show_numbers;
    private Dispatcher mDispatcher;
    private QueryFreeNumbersActionCreator mActionCreator;
    private QueryFreeNumbersStore mStore;

    public static QueryFreeNumbersFragment newInstance(){
        QueryFreeNumbersFragment instance = new QueryFreeNumbersFragment();
        return instance;
    }

    public static QueryFreeNumbersFragment newInstance(Bundle bundle){
        QueryFreeNumbersFragment instance = new QueryFreeNumbersFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.query_free_numbers_frag, container, false);
        initDependencies();
        setUpView(view);
        return view;
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mStore = QueryFreeNumbersStore.getInstance();
        mActionCreator = QueryFreeNumbersActionCreator.getInstance(mDispatcher);
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.query_free_numbers));
        rl_query_free_numbers = (RelativeLayout) view.findViewById(R.id.rl_query_free_numbers);
        rl_query_free_numbers.setOnClickListener(this);
        tv_show_numbers = (TextView) view.findViewById(R.id.tv_show_numbers);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_query_free_numbers:
                ProgressDialogUtils.get().showProgressDialog(getActivity(), getString(R.string.loading_tip));
                QueryFreeNumbersRequestBody requestBody = new QueryFreeNumbersRequestBody(UserInfo.get().appId);
                mActionCreator.requestQueryFreeNumbers(getActivity(), requestBody);
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
            Toast.makeText(getActivity(), getString(R.string.query_number_success), Toast.LENGTH_SHORT).show();
            tv_show_numbers.setText(mStore.getResponse().numbers);
        }else{
            Toast.makeText(getActivity(), String.valueOf(respCode), Toast.LENGTH_SHORT).show();
        }
    }
}
