package cn.part.wallet.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import cn.part.wallet.R;

public class PwdInputAlertDialog extends Dialog implements View.OnClickListener {

    private EditText mEtPwd;
    private TextView mTvCancel;
    private TextView mTvOk;

    public PwdInputAlertDialog(@NonNull Context context) {
        this(context, 0);
    }

    public PwdInputAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        View view = View.inflate(context, R.layout.pwd_input_dialog, null);
        mEtPwd = view.findViewById(R.id.dialog_pay_pwd_et_pwd);
        view.findViewById(R.id.dialog_pay_pwd_tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.dialog_pay_pwd_tv_ok).setOnClickListener(this);
        setCancelable(false);
        setContentView(view);
        changeDialogStyle();
    }

    /**
     * 设置dialog占满屏幕
     */
    private void changeDialogStyle() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
                window.setAttributes(attr);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_pay_pwd_tv_cancel:
                dismiss();
                break;
            case R.id.dialog_pay_pwd_tv_ok:
                String payPwd = mEtPwd.getText().toString();
                if (!TextUtils.isEmpty(payPwd)) {
                    if (mOnOkClick != null) {
                        mOnOkClick.onOkClick(payPwd);
                    }
                }
                dismiss();
                break;
        }
    }
    public void setOnOkClick(OnOkClick onOkClick){
        mOnOkClick = onOkClick;
    }
    private OnOkClick mOnOkClick;
    public interface OnOkClick{
        void onOkClick(String payPwd);
    }
}
