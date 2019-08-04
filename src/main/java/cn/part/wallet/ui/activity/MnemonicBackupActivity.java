package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.view.MnemonicBackupAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static cn.pedant.SweetAlert.SweetAlertDialog.BUTTON_CONFIRM;

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
        tvTitle.setText(R.string.title_mnemonic);
    }

    @Override
    public void initDatas() {
        getpDialog().changeAlertType(SweetAlertDialog.WARNING_TYPE);
        getpDialog().setContentText(getString(R.string.mnemonic_backup_alert_tips)).setConfirmButton(R.string.mnemonic_backup_alert_btn,(sw)->{dismissDialog();});
//        getpDialog().getButton(BUTTON_CONFIRM).setBackgroundResource(R.drawable.btn_red);
        showMyDialog(getString(R.string.mnemonic_backup_dailog_title));
//        MnemonicBackupAlertDialog mnemonicBackupAlertDialog = new MnemonicBackupAlertDialog(this);
//        mnemonicBackupAlertDialog.show();
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
