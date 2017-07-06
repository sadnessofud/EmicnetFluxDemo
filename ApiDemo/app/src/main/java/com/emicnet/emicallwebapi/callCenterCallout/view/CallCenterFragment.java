package com.emicnet.emicallwebapi.callCenterCallout.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emicnet.emicallwebapi.R;
import com.emicnet.emicallwebapi.callCenterCallout.activateUser.view.ActivateUserFragment;
import com.emicnet.emicallwebapi.callCenterCallout.callOut.view.CallOutFragment;
import com.emicnet.emicallwebapi.callCenterCallout.createUser.view.CreateUserFragment;

/**
 * Created by ShengWang on 2017/6/16.
 */

public class CallCenterFragment extends Fragment implements View.OnClickListener {
    private TextView tv_header_back,tv_header_title;
    private RelativeLayout rl_create_user,rl_activate_user,rl_call_out;

    public CallCenterFragment(){}

    public static CallCenterFragment newInstance(){
        CallCenterFragment instance = new CallCenterFragment();
        return instance;
    }

    public static CallCenterFragment newInstance(Bundle bundle){
        CallCenterFragment instance = new CallCenterFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_center_frag, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.call_center));
        rl_create_user = (RelativeLayout) view.findViewById(R.id.rl_create_user);
        rl_create_user.setOnClickListener(this);
        rl_activate_user = (RelativeLayout) view.findViewById(R.id.rl_activate_user);
        rl_activate_user.setOnClickListener(this);
        rl_call_out = (RelativeLayout) view.findViewById(R.id.rl_call_out);
        rl_call_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_create_user:
                swithToNewFragment(CreateUserFragment.newInstance());
                break;
            case R.id.rl_activate_user:
                swithToNewFragment(ActivateUserFragment.newInstance());
                break;
            case R.id.rl_call_out:
                swithToNewFragment(CallOutFragment.newInstance());
                break;
        }
    }

    private void swithToNewFragment(Fragment fragment) {
        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.hide(this);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commitAllowingStateLoss();
    }
}
