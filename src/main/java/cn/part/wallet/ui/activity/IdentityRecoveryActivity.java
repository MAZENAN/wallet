package cn.part.wallet.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.model.Metadata;
import org.consenlabs.tokencore.wallet.model.Network;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.viewmodel.GlobalOpViewModel;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class IdentityRecoveryActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_mnemonic_name)
    EditText etMnemonic;
    @BindView(R.id.et_identity_name)
    EditText etIdentityName;
    @BindView(R.id.et_identity_pwd)
    EditText etPwd;
    @BindView(R.id.et_identity_pwd_confirm)
    EditText etPwdConfirm;
    @BindView(R.id.et_identity_pwd_reminder_info)
    EditText etPwdReminder;
    @BindView(R.id.btn_recovery_identity)
    Button btnRecovery;

    private String mnemonic = "";
    private String identityName = "";
    private String identityPwd = "";
    private String pwdConfirm = "";
    private String pwdReminderInfo = "";
    private GlobalOpViewModel mViewModel;
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_identity_recovery;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(getString(R.string.title_identity_recovery));
    }

    @Override
    public void initDatas() {
        mViewModel = ViewModelProviders.of(this).get(GlobalOpViewModel.class);
        mViewModel.getLoadingData().observe(this,this::onLoadind);
        mViewModel.getRecoveryResultData().observe(this,this::onRecoveryIdentity);
    }

    private void onRecoveryIdentity(Boolean bool) {
        if (bool) {
            Intent intent =  new Intent(mContext,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
//           getpDialog().changeAlertType(SweetAlertDialog.ERROR_TYPE);
//           showMyDialog(getString(R.string.identity_recovery_fail));
            ToastUtil.showToast(R.string.identity_recovery_fail);
        }
    }

    private void onLoadind(Boolean bool) {
        switchDialog(bool,getString(R.string.alert_recovery_identity));
    }

    @Override
    public void configViews() {}

    @OnTextChanged({R.id.et_mnemonic_name,R.id.et_identity_pwd,R.id.et_identity_pwd_confirm,R.id.et_identity_pwd_reminder_info,R.id.et_identity_name})
    public void onTextChange(CharSequence text) {
        mnemonic = getEdtStr(etMnemonic);
        identityName = getEdtStr(etIdentityName);
        identityPwd = getEdtStr(etPwd);
        pwdConfirm = getEdtStr(etPwdConfirm);
        pwdReminderInfo = getEdtStr(etPwdReminder);
        boolean isEnable = !TextUtils.isEmpty(mnemonic) && !TextUtils.isEmpty(identityName) &&  !TextUtils.isEmpty(identityPwd) && !TextUtils.isEmpty(pwdConfirm);
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
                   mViewModel.postRecoveryIdentity(mnemonic,identityName,identityPwd,pwdReminderInfo);
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
