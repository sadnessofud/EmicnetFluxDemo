package com.emicnet.emicallwebapi.main.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.emicnet.emicallwebapi.R;

/**
 * Created by ShengWang on 2017/5/31.
 */

public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context, String strMessage) {
        this(context, R.style.CustomProgressDialog, strMessage);
    }

    public CustomProgressDialog(Context context, int theme, String strMessage) {
        super(context, theme);
        this.setContentView(R.layout.progress_dialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        TextView tvMsg = (TextView) this.findViewById(R.id.tv_progress);
        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
    }
}
