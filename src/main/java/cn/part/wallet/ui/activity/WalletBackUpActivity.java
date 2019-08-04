package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.utils.ToastUtil;

public class WalletBackUpActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_backup)
    TextView btnBackup;
    String walletMnemonic = "";
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_backup;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.title_backup);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        walletMnemonic = intent.getStringExtra("EthereumMnemonic");
    }

    @Override
    public void configViews() {
    }

    @OnClick(R.id.btn_backup)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_backup:
                Intent intent = new Intent(mContext,MnemonicBackupActivity.class);
                intent.putExtra("walletMnemonic",walletMnemonic);
                startActivity(intent);
                break;
        }
    }
}
