package com.emicnet.emicallwebapi.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.emicnet.emicallwebapi.main.view.CustomProgressDialog;

/**
 * Created by ShengWang on 2017/5/31.
 */

public class ProgressDialogUtils {

    private static ProgressDialogUtils instance;

    private ProgressDialogUtils(){};

    public static ProgressDialogUtils get(){
        if (instance == null){
            synchronized (ProgressDialogUtils.class){
                if (instance == null){
                    instance = new ProgressDialogUtils();
                }
            }
        }
        return instance;
    }

    private CustomProgressDialog mProgressDialog;

    public void showProgressDialog(Context context, String strMsg) {
        if (TextUtils.isEmpty(strMsg)) {
            return;
        }
        showProgressDialog(context, strMsg, true);
    }

    /**
     * show dialog
     *
     * @param strMsg
     *            msg to show
     * @param isCancelable
     *            can be canceled by press back
     */
    public void showProgressDialog(Context context, String strMsg, boolean isCancelable) {
        if (TextUtils.isEmpty(strMsg)) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog(context, strMsg);
        }
        mProgressDialog.setCancelable(isCancelable);
        mProgressDialog.show();
    }

    /**
     * dismiss dialog
     */
    public void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

    }
}
