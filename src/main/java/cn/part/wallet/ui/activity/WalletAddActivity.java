package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.consenlabs.tokencore.foundation.utils.MnemonicUtil;
import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.consenlabs.tokencore.wallet.keystore.EOSKeystore;
import org.consenlabs.tokencore.wallet.keystore.HDMnemonicKeystore;
import org.consenlabs.tokencore.wallet.keystore.IMTKeystore;
import org.consenlabs.tokencore.wallet.keystore.V3Keystore;
import org.consenlabs.tokencore.wallet.keystore.V3MnemonicKeystore;
import org.consenlabs.tokencore.wallet.model.BIP44Util;
import org.consenlabs.tokencore.wallet.model.ChainType;
import org.consenlabs.tokencore.wallet.model.Metadata;
import org.consenlabs.tokencore.wallet.model.MnemonicAndPath;
import org.consenlabs.tokencore.wallet.model.Network;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.MyThreadPool;
import cn.part.wallet.utils.ToastUtil;

public class WalletAddActivity extends BaseActivity {
    @BindView(R.id.img_wallet_icon)
    ImageView imgWallet;
    @BindView(R.id.tv_wallet_remind)
    TextView tvRemind;

    @BindView(R.id.et_wallet_name)
    EditText etWalletName;
    @BindView(R.id.et_wallet_pwd)
    EditText etWalletPwd;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etWalletPwdAgain;
    @BindView(R.id.et_wallet_pwd_reminder_info)
    EditText etWalletPwdRemind;
    @BindView(R.id.btn_create_wallet)
    TextView btnCreate;

    private String walletType;
    private String walletName;
    private String pass;
    private String passConfirm;
    private String passRemind;

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_add;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        walletType = intent.getStringExtra("wallet_type");
    }

    @Override
    public void configViews() {
        String tv = "";
        int drawableId = R.drawable.eth;
        switch (walletType){
            case ChainType.ETHEREUM:
                drawableId = R.drawable.eth;
                break;
            case ChainType.BITCOIN:
                drawableId = R.drawable.btc;
                break;
            case ChainType.EOS:
                drawableId = R.drawable.eos;
                break;
        }
        tv = "创建 "+walletType+" 钱包";
        tvRemind.setText(tv);
        imgWallet.setImageResource(drawableId);
    }

    @OnTextChanged({R.id.et_wallet_name,R.id.et_wallet_pwd,R.id.et_wallet_pwd_again,R.id.et_wallet_pwd_reminder_info})
    public void onTextChange(CharSequence text) {
        walletName = getEdtStr(etWalletName);
        pass = getEdtStr(etWalletPwd);
        passConfirm = getEdtStr(etWalletPwdAgain);
        passRemind = getEdtStr(etWalletPwdRemind);

        boolean isEnable = !TextUtils.isEmpty(walletName)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(passConfirm) ? true : false;
        btnCreate.setEnabled(isEnable);
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
                        try{
                            LogUtils.e(TAG,"开始创建钱包");
                            createWallet(walletName,pass,passRemind);
                            LogUtils.e(TAG,"创建钱包成功");
                        }catch (Exception e){
                            LogUtils.e(TAG,"创建钱包失败异常信息： "+e.getMessage());
                        }

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

    private String getEdtStr(EditText editText){
        return editText.getText().toString().trim();
    }

    private void createWallet(String walletName,String pass,String hint) {
        Identity identity = Identity.getCurrentIdentity();
        List<String> mnemonics = MnemonicUtil.randomMnemonicCodes();
        LogUtils.i(TAG,"助记词为"+mnemonics.toString());
        Metadata walletMetadata = new Metadata();
        walletMetadata.setPasswordHint(hint);
//        walletMetadata.setSource(Metadata.FROM_MNEMONIC); //TODO 不确定
        walletMetadata.setSource(Metadata.NONE);
        walletMetadata.setName(walletName);

        IMTKeystore keystore = null;
        switch (walletType){
            case ChainType.ETHEREUM:
                walletMetadata.setChainType(ChainType.ETHEREUM);
                String ETHEREUM_HD_PATH = "m/44'/60'/0'/0/0";
                keystore = V3MnemonicKeystore.create(walletMetadata, pass, mnemonics, ETHEREUM_HD_PATH);
                break;
            case ChainType.BITCOIN:
                walletMetadata.setChainType(ChainType.BITCOIN);
                walletMetadata.setSegWit(Metadata.P2WPKH);
                String path = BIP44Util.BITCOIN_SEGWIT_MAIN_PATH;
                keystore = HDMnemonicKeystore.create(walletMetadata, pass, mnemonics, path);

                //来自identity源码
//                Metadata walletMetadata = new Metadata();
//                walletMetadata.setChainType(ChainType.BITCOIN);
//                walletMetadata.setPasswordHint(this.getMetadata().getPasswordHint());
//                walletMetadata.setSource(this.getMetadata().getSource());
//                walletMetadata.setNetwork(this.getMetadata().getNetwork());
//                walletMetadata.setName("BTC");
//                walletMetadata.setSegWit(segWit);
//                String path;
//                if (Metadata.P2WPKH.equals(segWit)) {
//                    path =  this.getMetadata().isMainNet() ? BIP44Util.BITCOIN_SEGWIT_MAIN_PATH : BIP44Util.BITCOIN_SEGWIT_TESTNET_PATH;
//                } else {
//                    path = this.getMetadata().isMainNet() ? BIP44Util.BITCOIN_MAINNET_PATH : BIP44Util.BITCOIN_TESTNET_PATH;
//                }
//
//                IMTKeystore keystore = HDMnemonicKeystore.create(walletMetadata, password, mnemonicCodes, path);
//                return WalletManager.createWallet(keystore);

                break;
            case ChainType.EOS:
                walletMetadata.setChainType(ChainType.EOS);
                keystore = EOSKeystore.create(walletMetadata, pass, "", mnemonics, BIP44Util.EOS_LEDGER, null);
                break;
        }

        Wallet wallet = new Wallet(keystore);
        identity.addWallet(wallet);

//        if (null != identity) {
//            Wallet ethereumWallet = identity.getWallets().get(0);
//            if (null != ethereumWallet) {
//                String ethereumId = ethereumWallet.getId();
//                MnemonicAndPath ethereumMnemonic = WalletManager.exportMnemonic(ethereumId, passwd);
//                LogUtils.i(TAG,ethereumMnemonic.getMnemonic());
//                SharedPreferences.Editor editor = getSharedPreferences("default_wallet", MODE_PRIVATE).edit();
//                editor.putString("current_wallet_id", ethereumWallet.getId());
//                editor.commit();
//                MyThreadPool.runOnUiThread(()->{
//                    getDialog().showSuccess();
//                    showDialog("创建成功");
//                    MyThreadPool.runOnUiThreadDelayed(()->{
//                        hideDialog();
//                        Intent intent = new Intent(mContext, WalletBackUpActivity.class);
//                        intent.putExtra("EthereumMnemonic", ethereumMnemonic.getMnemonic());
//                        startActivity(intent);
//                    },1000);
//                });
//            } else {
//                MyThreadPool.runOnUiThread(()->{
//                            getDialog().showFail();
//                            showDialog("获取钱包失败");
//                            MyThreadPool.runOnUiThreadDelayed(()->{
//                                hideDialog(); },1000);
//                        }
//                );
//            }
//        } else {
//            MyThreadPool.runOnUiThread(()->{
//                        getDialog().showFail();
//                        showDialog("创建钱包失败");
//                        MyThreadPool.runOnUiThreadDelayed(()->{
//                            hideDialog(); },1000
//                        );
//                    }
//            );
//        }
    }
}
