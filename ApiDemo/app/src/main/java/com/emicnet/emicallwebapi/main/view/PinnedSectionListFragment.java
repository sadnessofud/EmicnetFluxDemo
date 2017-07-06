package com.emicnet.emicallwebapi.main.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.emicnet.emicallwebapi.R;
import com.emicnet.emicallwebapi.callCenterCallout.view.CallCenterFragment;
import com.emicnet.emicallwebapi.createNumberPair.view.CreateNumberPairFragment;
import com.emicnet.emicallwebapi.createSubAccount.view.CreateSubAccountFragment;
import com.emicnet.emicallwebapi.remote.bean.Function;
import com.emicnet.emicallwebapi.teleConference.view.TelConferenceFragment;
import com.emicnet.emicallwebapi.twoWayCall.view.TwoWayCallFragment;
import com.emicnet.emicallwebapi.voiceCode.view.VoiceCodeFragment;
import com.emicnet.emicallwebapi.voiceNotify.view.VoiceNotifyFragment;

import de.halfbit.pinnedsection.PinnedSectionListView;


/**
 * Created by ShengWang on 2017/5/25.
 */

public class PinnedSectionListFragment extends ListFragment {

    private static PinnedSectionListFragment instance;
/*    private ActionsCreator mActionsCreator;
    private Dispatcher mDispatcher;*/

    public PinnedSectionListFragment() {
    }

    public static PinnedSectionListFragment newInstance() {
        if (instance == null) {
            synchronized (PinnedSectionListFragment.class) {
                instance = new PinnedSectionListFragment();
            }
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (view == null) {
            view = inflater.inflate(R.layout.list_frag, container, false);
        }
        initializeAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item item = (Item) getListView().getAdapter().getItem(position);
        if (item != null && item.type == Item.ITEM) {
            switch (item.func){
                case Function.VOICE_CODE:
                    swithToNewFragment(VoiceCodeFragment.newInstance());
                    break;
                case Function.CALLBACK:
                    swithToNewFragment(TwoWayCallFragment.newInstance());
                    break;
                case Function.TEL_CONFERENE:
                    swithToNewFragment(TelConferenceFragment.newInstance());
                    break;
                case Function.VOICE_NOTIFY:
                    swithToNewFragment(VoiceNotifyFragment.newInstance());
                    break;
                case Function.CREATE_NUMBER_PAIR:
                    swithToNewFragment(CreateNumberPairFragment.newInstance());
                    break;
                case Function.CREATE_SUBACCOUNT:
                    swithToNewFragment(CreateSubAccountFragment.newInstance());
                    break;
                case Function.CALL_CENTER:
                    swithToNewFragment(CallCenterFragment.newInstance());
                    break;
            }
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

    private void initializeAdapter() {
        setListAdapter(new SimpleAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1));
    }

    static class SimpleAdapter extends ArrayAdapter<Item> implements PinnedSectionListView.PinnedSectionListAdapter {

        public SimpleAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
            generateDataset();
        }

        public void generateDataset() {
            Item item0 = new Item(Item.SECTION, "语音通讯功能", "");
            add(item0);
            Item item00 = new Item(Item.ITEM, "语音验证码", Function.VOICE_CODE);
            add(item00);
            Item item01 = new Item(Item.ITEM, "APP双向呼叫", Function.CALLBACK);
            add(item01);
            Item item02 = new Item(Item.ITEM, "电话会议", Function.TEL_CONFERENE);
            add(item02);
            Item item1 = new Item(Item.SECTION, "语音文件管理功能", "");
            add(item1);
            Item item10 = new Item(Item.ITEM, "语音通知", Function.VOICE_NOTIFY);
            add(item10);
            Item item2 = new Item(Item.SECTION, "云总机和企业用户管理功能", "");
            add(item2);
            Item item20 = new Item(Item.ITEM, "号码保护", Function.CREATE_NUMBER_PAIR);
            add(item20);
            Item item3 = new Item(Item.SECTION, "子账户管理功能", "");
            add(item3);
            Item item30 = new Item(Item.ITEM, "添加子账户", Function.CREATE_SUBACCOUNT);
            add(item30);
            Item item4 = new Item(Item.SECTION, "呼叫中心", "");
            add(item4);
            Item item40 = new Item(Item.ITEM, "呼叫中心", Function.CALL_CENTER);
            add(item40);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTextColor(Color.DKGRAY);
            view.setTag("" + position);
            Item item = getItem(position);
            if (item.type == Item.SECTION) {
                view.setBackgroundColor(Color.parseColor("#F2F2F2"));
            }
            return view;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).type;
        }

        @Override
        public boolean isItemViewTypePinned(int viewType) {
            return viewType == Item.SECTION;
        }

    }

    static class Item {

        public static final int ITEM = 0;
        public static final int SECTION = 1;

        public int type;
        public String text;
        public String func;

        public Item(int type, String text, String func) {
            this.type = type;
            this.text = text;
            this.func = func;
        }

        @Override
        public String toString() {
            return text;
        }

    }
}
