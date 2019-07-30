package cn.part.wallet.view.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import cn.part.wallet.R;

public class CustomDialog extends Dialog {

    private TextView tvProgress;
    private LoadingView loadingView;
    private View viewSuccess;
    private View viewFail;

    public CustomDialog(Context context) {
        this(context, 0);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private View inflateView(Context ct) {
        View v = View.inflate(ct, R.layout.common_progress_view, null);
        loadingView = (LoadingView)v.findViewById(R.id.loadingView);
        tvProgress = (TextView)v.findViewById(R.id.tv_progress);
        viewSuccess = v.findViewById(R.id.loadingSucc);
        viewFail = v.findViewById(R.id.loadingFail);
        return v;
    }

    public static CustomDialog instance(Context context) {
        CustomDialog dialog = new CustomDialog(context, R.style.loading_dialog);
        View v = dialog.inflateView(context);
        dialog.setContentView(v,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        );

        dialog.setCancelable(false);
        dialog.changeDialogStyle();
        return dialog;

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

    public CustomDialog setTvProgress(String progressTip) {
        tvProgress.setText(progressTip);
        return this;
    }

    public void showSuccess() {
        loadingView.setVisibility(View.GONE);
        viewFail.setVisibility(View.GONE);
        viewSuccess.setVisibility(View.VISIBLE);
    }

    public void showFail() {
        loadingView.setVisibility(View.GONE);
        viewFail.setVisibility(View.VISIBLE);
        viewSuccess.setVisibility(View.GONE);
    }

    public void showLoadind() {
        loadingView.setVisibility(View.VISIBLE);
        viewFail.setVisibility(View.GONE);
        viewSuccess.setVisibility(View.GONE);
    }
}