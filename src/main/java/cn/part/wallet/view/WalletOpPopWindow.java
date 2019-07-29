package cn.part.wallet.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import cn.part.wallet.R;

public class WalletOpPopWindow extends PopupWindow {
    public WalletOpPopWindow(Context context,walletOpListener listener) {
        super(context);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View contentView = LayoutInflater.from(context).inflate(R.layout.popwindow_choose_wallet_op,
                null, false);
        setContentView(contentView);
        View viewAdd = contentView.findViewById(R.id.tv_wallet_op_add);
        View viewImport = contentView.findViewById(R.id.tv_wallet_op_import);
        View viewCancle = contentView.findViewById(R.id.tv_cancle);
        if (listener != null) {
            viewAdd.setOnClickListener((view)->{listener.walletCreate();});
            viewImport.setOnClickListener((view)->{listener.walletImport();});
            viewCancle.setOnClickListener((view)->{listener.cancle();});
        }
    }

    public interface walletOpListener {
        void walletCreate();
        void walletImport();
        void cancle();
    }
}
