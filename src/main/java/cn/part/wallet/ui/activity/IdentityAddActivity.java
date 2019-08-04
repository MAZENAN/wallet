package cn.part.wallet.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.utils.ActivityManager;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.viewmodel.GlobalOpViewModel;

public class IdentityAddActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_wallet_name)
    EditText etIdentityName;
    @BindView(R.id.et_wallet_pwd)
    EditText etPass;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etPassConfirm;
    @BindView(R.id.et_wallet_pwd_reminder_info)
    EditText etPassPrompt;
    @BindView(R.id.btn_create_wallet)
    Button btnCreateWallet;
    @BindView(R.id.btn_input_wallet)
    Button btnImportWallet;

    private String identityName = "";
    private String pass = "";
    private String passConfirm = "";
    private String passRemind = "";
    private GlobalOpViewModel mViewModel;

    @Override
    protected void onBeforeSetContentLayout() {}

    @Override
    public int getLayoutId() {
        return R.layout.activity_identity_add;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.title_create_identity);
    }

    @Override
    public void initDatas() {
        mViewModel = ViewModelProviders.of(this).get(GlobalOpViewModel.class);
        mViewModel.getLoadingData().observe(this,this::onLoading);
        mViewModel.getMnemonicData().observe(this,this::onGetMnemonic);
    }

    @Override
    public void configViews() {}

    @OnTextChanged({R.id.et_wallet_name,R.id.et_wallet_pwd,R.id.et_wallet_pwd_again,R.id.et_wallet_pwd_reminder_info})
    public void onTextChange(CharSequence text) {
        identityName = getEdtStr(etIdentityName);
        pass = getEdtStr(etPass);
        passConfirm = getEdtStr(etPassConfirm);
        passRemind = getEdtStr(etPassPrompt);
        boolean isEnable = !TextUtils.isEmpty(identityName) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(passConfirm);
        btnCreateWallet.setEnabled(isEnable);
      }

    @OnClick(R.id.btn_create_wallet)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_create_wallet:
                boolean res = checkInput();
                if (res){
                    mViewModel.postCreateIdentity(identityName,pass,passRemind);
                }
                break;
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(identityName)){
            ToastUtil.showToast(R.string.create_identity_name_input_tips);
            return false;
        }
        if (TextUtils.isEmpty(pass)){
            ToastUtil.showToast(R.string.create_wallet_pwd_input_tips);
            return false;
        }
        if (TextUtils.isEmpty(passConfirm)){
            ToastUtil.showToast(R.string.create_wallet_pwdconfirm_input_tips);
            return false;
        }
        if (!pass.trim().equals(passConfirm)){
            ToastUtil.showToast(R.string.create_wallet_pwd_confirm_input_tips);
            return false;
        }
        return true;
    }

    private void onGetMnemonic(String mnemonic) {
        if (!mnemonic.isEmpty()) {
                ToastUtil.showToast(R.string.create_identity_success);
                Intent intent = new Intent(mContext, WalletBackUpActivity.class);
                intent.putExtra("EthereumMnemonic", mnemonic);
                ActivityManager.getInstance().finishAllActivity();
                startActivity(intent);
        }else {
            ToastUtil.showToast(R.string.create_identity_fail);
        }
    }

    private void onLoading(Boolean bool) {
        switchDialog(bool,getString(R.string.doing_create_identity));
    }

    private String getEdtStr(EditText editText){
        return editText.getText().toString().trim();
    }
}
