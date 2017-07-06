package com.emicnet.emicallwebapi.main.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.emicnet.emicallwebapi.R;

/**
 * Flux的Controller-View模块
 * Created by ntop on 18/12/15.
 */
public class EmicallWebApiAcitivity extends AppCompatActivity {
    private PinnedSectionListFragment mSectionListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        setupView();
    }



    private void setupView() {
        FragmentTransaction transaction;
        mSectionListFragment = PinnedSectionListFragment.newInstance();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, mSectionListFragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mSectionListFragment = null;
        super.onDestroy();
    }

}
