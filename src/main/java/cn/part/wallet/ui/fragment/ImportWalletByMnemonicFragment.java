package cn.part.wallet.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.consenlabs.tokencore.wallet.model.BIP44Util;
import org.consenlabs.tokencore.wallet.model.ChainType;
import org.consenlabs.tokencore.wallet.model.Messages;
import org.consenlabs.tokencore.wallet.model.Metadata;
import org.consenlabs.tokencore.wallet.model.Network;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseFragment;
import cn.part.wallet.base.NoBarBaseFragment;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;

public class ImportWalletByMnemonicFragment extends NoBarBaseFragment {

    private static final String WALLET_TYPE = "wallet_type";

    @BindView(R.id.et_mnemonic)
    EditText etMnemonic;
    @BindView(R.id.et_standard)
    EditText etStandard;
    @BindView(R.id.et_wallet_pwd)
    EditText etWalletPwd;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etWalletPwdAgain;
    @BindView(R.id.et_wallet_pwd_reminder_info)
    EditText etPwdHint;
    @BindView(R.id.btn_import_wallet)
    TextView btnImport;

    private String walletType;
    private String mnemonic;
    private String standard;
    private String pass;
    private String pwdConfirm;
    private String pwdHint;

    public static ImportWalletByMnemonicFragment newInstance(String param) {
        ImportWalletByMnemonicFragment fragment = new ImportWalletByMnemonicFragment();
        Bundle args = new Bundle();
        args.putString(WALLET_TYPE, param);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_import_wallet_by_mnemonic;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        Bundle bundle = getArguments();
        if (bundle != null){
            walletType = bundle.getString(WALLET_TYPE);
        }
    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.btn_import_wallet)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_import_wallet:
                boolean bool = checkInput();
                if (bool) {
                    try {
                        LogUtils.i("import","通过助记词开始导入"+walletType +"钱包");
                        importWalletByMnemonic();
                        LogUtils.i("import","助记词开始导入"+walletType +"钱包成功");
                    }catch (Exception e) {
                        LogUtils.e("import",e.getMessage());
                    }

                }
                break;
        }
    }

    @OnTextChanged({R.id.et_mnemonic,R.id.et_standard,R.id.et_wallet_pwd,R.id.et_wallet_pwd_again})
    public void onTextChange(CharSequence text) {
        mnemonic = getEdtStr(etMnemonic);
        pass = getEdtStr(etWalletPwd);
        standard = getEdtStr(etStandard);
        pwdConfirm = getEdtStr(etWalletPwdAgain);
        pwdHint = getEdtStr(etPwdHint);

        boolean isEnable = !TextUtils.isEmpty(mnemonic)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(standard)&&!TextUtils.isEmpty(pwdConfirm) ? true : false;
        btnImport.setEnabled(isEnable);
    }

    private String getEdtStr(EditText editText){
        return editText.getText().toString().trim();
    }

    private void importWalletByMnemonic() {
//        String m = "victory truck motion urge loyal supply pipe ship assume code where hub";
//        Wallet wallet = WalletManager.importWalletFromMnemonic(
//                new Metadata(ChainType.ETHEREUM, Network.MAINNET, "name", "passwordHint"),
//                m, BIP44Util.ETHEREUM_PATH, SampleKey.PASSWORD, true);
//
//        assertEquals(wallet.getAddress(), "30ee43ceea07f72511eb8b94eb1cacc4e21f0239");
//        assertEquals(wallet.hasMnemonic(), true);
//
//        m = "attack curtain frog balcony trash base twenty history cradle cruel illness attitude";
//        wallet = WalletManager.importWalletFromMnemonic(
//                new Metadata(ChainType.BITCOIN, Network.TESTNET, "name", "passwordHint"),
//                m, BIP44Util.BITCOIN_TESTNET_PATH, SampleKey.PASSWORD, true);
//
//        assertEquals(wallet.getAddress(), "ms8xH9qe3rg4DPHJty5baTT8teTMQNsZFT");
//        assertEquals(wallet.hasMnemonic(), true);
//        wallet.delete(SampleKey.PASSWORD);
//
//        try {
//            m = "attack curtain frog balcony trash base twenty history cradle cruel illness attitud";
//            wallet = WalletManager.importWalletFromMnemonic(
//                    new Metadata(ChainType.BITCOIN, Network.MAINNET, "name", "passwordHint"),
//                    m, BIP44Util.BITCOIN_MAINNET_PATH, SampleKey.PASSWORD, true);
//
//            assertEquals("1", "2");
//        } catch (Exception ex) {
//            assertEquals(ex.getMessage(), Messages.MNEMONIC_BAD_WORD);
//        }
        //new Metadata(ChainType.ETHEREUM, Network.MAINNET, "name", "passwordHint")
        Metadata metadata = new Metadata();
        metadata.setChainType(walletType);
        metadata.setNetwork(Network.MAINNET);
        metadata.setPasswordHint(pwdHint);
        metadata.setName("import-"+walletType);
        if (walletType.equals(ChainType.BITCOIN)){
//            standard = BIP44Util.BITCOIN_MAINNET_PATH;//TODO 切换选择
//            standard = BIP44Util.BITCOIN_SEGWIT_MAIN_PATH;//TODO 切换选择
//            standard = BIP44Util.BITCOIN_TESTNET_PATH;//TODO 切换选择
            standard = BIP44Util.BITCOIN_SEGWIT_TESTNET_PATH;//TODO 切换选择
        }
        WalletManager.importWalletFromMnemonic(metadata,mnemonic,standard,pass,true);

    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(mnemonic)){
            ToastUtil.showToast(R.string.import_wallet_mnemonic_input_tips);
            return false;
        }
        if (TextUtils.isEmpty(pass)){
            ToastUtil.showToast(R.string.import_wallet_pwd_input_tips);
            return false;
        }
        if (TextUtils.isEmpty(pwdConfirm)){
            ToastUtil.showToast(R.string.import_wallet_pwd_confirm_input_tips);
            return false;
        }
        if (!pass.equals(pwdConfirm)){
            ToastUtil.showToast(R.string.import_wallet_confirm_input_tips);
            return false;
        }
        return true;
    }
}
