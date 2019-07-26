package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
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
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.MyThreadPool;
import cn.part.wallet.utils.ToastUtil;

public class IdentityAddActivity extends BaseActivity {
    private static final String TAG = IdentityAddActivity.class.toString();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_wallet_name)
    EditText etWalletName;
    @BindView(R.id.et_wallet_pwd)
    EditText etPass;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etPassConfirm;
    @BindView(R.id.et_wallet_pwd_reminder_info)
    EditText etPassPrompt;
    @BindView(R.id.btn_create_wallet)
    TextView btnCreateWallet;
    @BindView(R.id.btn_input_wallet)
    TextView btnImportWallet;

    private String walletName = "";
    private String pass = "";
    private String passConfirm = "";
    private String passRemind = "";

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_identity_add;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("创建钱包");
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @OnTextChanged({R.id.et_wallet_name,R.id.et_wallet_pwd,R.id.et_wallet_pwd_again,R.id.et_wallet_pwd_reminder_info})
    public void onTextChange(CharSequence text) {
        walletName = getEdtStr(etWalletName);
        pass = getEdtStr(etPass);
        passConfirm = getEdtStr(etPassConfirm);
        passRemind = getEdtStr(etPassPrompt);

        boolean isEnable = !TextUtils.isEmpty(walletName)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(passConfirm) ? true : false;
        btnCreateWallet.setEnabled(isEnable);
      }

      private String getEdtStr(EditText editText){
        return editText.getText().toString().trim();
    }

    @OnClick(R.id.btn_create_wallet)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_create_wallet:
                boolean res = checkInput();
                if (res){
                    getDialog().showLoadind();
                    showDialog("正在创建钱包...");
                    MyThreadPool.execute(()->{
                        createWallet(walletName,pass,passRemind);
                        }
                    );
                }
                break;
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(walletName)){
            ToastUtil.showToast(R.string.create_wallet_name_input_tips);
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

    private void createWallet(String identityName,String passwd,String hint) {
        Identity identity = Identity.createIdentity(identityName, passwd, hint, Network.MAINNET, Metadata.P2WPKH);
        if (null != identity) {
            Wallet ethereumWallet = identity.getWallets().get(0);
            if (null != ethereumWallet) {
                String ethereumId = ethereumWallet.getId();
                MnemonicAndPath ethereumMnemonic = WalletManager.exportMnemonic(ethereumId, passwd);
                LogUtils.i(TAG,ethereumMnemonic.getMnemonic());
                SharedPreferences.Editor editor = getSharedPreferences("default_wallet", MODE_PRIVATE).edit();
                editor.putString("current_wallet_id", ethereumWallet.getId());
                editor.commit();
                MyThreadPool.runOnUiThread(()->{
                    getDialog().showSuccess();
                    showDialog("创建成功");
                    MyThreadPool.runOnUiThreadDelayed(()->{
                        hideDialog();
                        Intent intent = new Intent(mContext, WalletBackUpActivity.class);
                        intent.putExtra("EthereumMnemonic", ethereumMnemonic.getMnemonic());
                        startActivity(intent);
                    },1000);
                });
            } else {
                MyThreadPool.runOnUiThread(()->{
                    getDialog().showFail();
                    showDialog("获取钱包失败");
                    MyThreadPool.runOnUiThreadDelayed(()->{
                        hideDialog(); },1000);
                    }
                );
            }
        } else {
            MyThreadPool.runOnUiThread(()->{
                getDialog().showFail();
                showDialog("创建钱包失败");
                MyThreadPool.runOnUiThreadDelayed(()->{
                    hideDialog(); },1000
                );
                }
            );
        }
    }
}
