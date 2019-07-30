package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.view.MnemonicBackupAlertDialog;

public class MnemonicBackupActivity extends BaseActivity {
    String walletMnemonic = "";

    @BindView(R.id.tv_mnemonic)
    TextView tvMnemonic;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mnemonic_back;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("助记词");
    }

    @Override
    public void initDatas() {
        MnemonicBackupAlertDialog mnemonicBackupAlertDialog = new MnemonicBackupAlertDialog(this);
        mnemonicBackupAlertDialog.show();
        Intent intent = getIntent();
        walletMnemonic = intent.getStringExtra("walletMnemonic");
        tvMnemonic.setText(walletMnemonic);
    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                Intent intent = new Intent(mContext,VerifyMnemonicBackupActivity.class);
                intent.putExtra("walletMnemonic",walletMnemonic);
                startActivity(intent);
                break;
        }
    }
}
