package cn.part.wallet.ui.activity;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.consenlabs.tokencore.wallet.model.Metadata;
import org.consenlabs.tokencore.wallet.model.MnemonicAndPath;
import org.consenlabs.tokencore.wallet.model.Network;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.ui.fragment.MineFragment;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;

public class IdentityRecoveryActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_mnemonic_name)
    EditText etMnemonic;
    @BindView(R.id.et_identity_pwd)
    EditText etPwd;
    @BindView(R.id.et_identity_pwd_confirm)
    EditText etPwdConfirm;
    @BindView(R.id.et_identity_pwd_reminder_info)
    EditText etPwdReminder;
    @BindView(R.id.btn_recovery_identity)
    TextView btnRecovery;

    private String mnemonic = "";
    private String identityPwd = "";
    private String pwdConfirm = "";
    private String pwdReminderInfo = "";
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_identity_recovery;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("恢复身份");
    }

    @Override
    public void initDatas() {
    }

    @Override
    public void configViews() {

    }

    @OnTextChanged({R.id.et_mnemonic_name,R.id.et_identity_pwd,R.id.et_identity_pwd_confirm,R.id.et_identity_pwd_reminder_info})
    public void onTextChange(CharSequence text) {
        mnemonic = getEdtStr(etMnemonic);
        identityPwd = getEdtStr(etPwd);
        pwdConfirm = getEdtStr(etPwdConfirm);
        pwdReminderInfo = getEdtStr(etPwdReminder);

        boolean isEnable = !TextUtils.isEmpty(mnemonic)&&!TextUtils.isEmpty(identityPwd)&&!TextUtils.isEmpty(pwdConfirm) ? true : false;
        btnRecovery.setEnabled(isEnable);
    }

    private String getEdtStr(EditText editText){
        return editText.getText().toString().trim();
    }

    @OnClick(R.id.btn_recovery_identity)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_recovery_identity:
                boolean b = checkInput();
                if (b) {
                    try{
                        Identity identity = Identity.recoverIdentity(mnemonic, "xyz", identityPwd, pwdReminderInfo, Network.MAINNET, Metadata.P2WPKH);
                        Wallet ethereumWallet = identity.getWallets().get(0);
                        String ethereumId = ethereumWallet.getId();
                        SharedPreferences.Editor editor = getSharedPreferences("default_wallet", MODE_PRIVATE).edit();
                        editor.putString("current_wallet_id", ethereumId);
                        launchActivity(MainActivity.class);
                    }catch (Exception e){
                        ToastUtil.showToast("恢复身份失败");
                    }
                }
                break;
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(mnemonic)){
            ToastUtil.showToast(R.string.recovery_mnemonic_input_tips);
            return false;
        }
        if (TextUtils.isEmpty(identityPwd)){
            ToastUtil.showToast(R.string.recovery_identity_pwd_input_tips);
            return false;
        }
        if (TextUtils.isEmpty(pwdConfirm)){
            ToastUtil.showToast(R.string.recovery_identity_pwd_confirm_input_tips);
            return false;
        }
        if (!identityPwd.trim().equals(pwdConfirm)){
            ToastUtil.showToast(R.string.recovery_identity_confirm_input_tips);
            return false;
        }
        return true;
    }
}
