package com.emicnet.emicallwebapi.createNumberPair.view;

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
import com.emicnet.emicallwebapi.createNumberPair.createGroup.view.CreateGroupFragment;
import com.emicnet.emicallwebapi.createNumberPair.matchNumber.view.MatchNumberFragment;
import com.emicnet.emicallwebapi.createNumberPair.queryFreeNumbers.view.QueryFreeNumbersFragment;

/**
 * Created by ShengWang on 2017/6/15.
 */

public class CreateNumberPairFragment extends Fragment implements View.OnClickListener {
    private TextView tv_header_back,tv_header_title;
    private RelativeLayout rl_match_number,rl_create_group,rl_get_free_num;

    public CreateNumberPairFragment(){};

    public static CreateNumberPairFragment newInstance(){
        return new CreateNumberPairFragment();
    }

    public static CreateNumberPairFragment newInstance(Bundle bundle){
        CreateNumberPairFragment instance = new CreateNumberPairFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_num_pair_frag, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        tv_header_back = (TextView) view.findViewById(R.id.tv_header_back);
        tv_header_back.setOnClickListener(this);
        tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.create_num_pair));
        rl_match_number = (RelativeLayout) view.findViewById(R.id.rl_match_number);
        rl_match_number.setOnClickListener(this);
        rl_create_group = (RelativeLayout) view.findViewById(R.id.rl_create_group);
        rl_create_group.setOnClickListener(this);
        rl_get_free_num = (RelativeLayout) view.findViewById(R.id.rl_get_free_num);
        rl_get_free_num.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_back:
                getActivity().onBackPressed();
                break;
            case R.id.rl_match_number:
                swithToNewFragment(MatchNumberFragment.newInstance());
                break;
            case R.id.rl_create_group:
                swithToNewFragment(CreateGroupFragment.newInstance());
                break;
            case R.id.rl_get_free_num:
                swithToNewFragment(QueryFreeNumbersFragment.newInstance());
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
