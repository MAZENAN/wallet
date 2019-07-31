package cn.part.wallet.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.bitcoinj.core.ECKey;
import org.consenlabs.tokencore.foundation.utils.NumericUtil;
import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.consenlabs.tokencore.wallet.model.ChainType;
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

public class ImportWalletByPrivatekeyFragment extends NoBarBaseFragment {
    @BindView(R.id.et_private_key)
    EditText etPrivatekey;
    @BindView(R.id.et_wallet_pwd)
    EditText etPwd;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etEtPwdAgain;
    @BindView(R.id.et_wallet_pwd_reminder_info)
    EditText etPwdHint;
    @BindView(R.id.btn_import_wallet)
    TextView btnBegin;

    private String privateKey;
    private String pass;
    private String passConfirm;
    private String hint;
    private static final String WALLET_TYPE = "wallet_type";
    private String walletType;

    public static ImportWalletByPrivatekeyFragment newInstance(String param) {
        ImportWalletByPrivatekeyFragment fragment = new ImportWalletByPrivatekeyFragment();
        Bundle args = new Bundle();
        args.putString(WALLET_TYPE, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_import_wallet_by_privatekey;
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

    @OnTextChanged({R.id.et_private_key,R.id.et_wallet_pwd,R.id.et_wallet_pwd_again,R.id.et_wallet_pwd_reminder_info})
    public void onTextChange(CharSequence text) {
        privateKey = getEdtStr(etPrivatekey);
        pass = getEdtStr(etPwd);
        passConfirm = getEdtStr(etEtPwdAgain);
        hint = getEdtStr(etPwdHint);

        boolean isEnable = !TextUtils.isEmpty(privateKey)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(passConfirm) ? true : false;
        btnBegin.setEnabled(isEnable);
    }

    private String getEdtStr(EditText editText){
        return editText.getText().toString().trim();
    }

    @OnClick(R.id.btn_import_wallet)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_import_wallet:
                boolean bool = checkInput();
                if (bool) {
                    try {
                        LogUtils.i("import","通过私钥开始导入"+walletType +"钱包");
                        importWalletByPrivateKey();
                        LogUtils.i("import","私钥开始导入"+walletType +"钱包成功");
                    }catch (Exception e) {
                        LogUtils.e("import",e.getMessage());
                    }
                }
                break;
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(privateKey)){
            ToastUtil.showToast(R.string.import_wallet_private_input_tips);
            return false;
        }
        if (TextUtils.isEmpty(pass)){
            ToastUtil.showToast(R.string.import_wallet_pwd_input_tips);
            return false;
        }
        if (TextUtils.isEmpty(passConfirm)){
            ToastUtil.showToast(R.string.import_wallet_pwd_confirm_input_tips);
            return false;
        }
        if (!pass.equals(passConfirm)){
            ToastUtil.showToast(R.string.import_wallet_confirm_input_tips);
            return false;
        }
        return true;
    }

    private void importWalletByPrivateKey() {
//        Wallet wallet = WalletManager.importWalletFromPrivateKey(new Metadata(ChainType.ETHEREUM, Network.MAINNET, "name", "passwordHint"),
//                SampleKey.PRIVATE_KEY_STRING,
//                SampleKey.PASSWORD, true);
//
//        ECKey key = ECKey.fromPrivate(wallet.decryptMainKey(SampleKey.PASSWORD));
//        assertEquals(
//                NumericUtil.bigIntegerToHex(key.getPrivKey()),
//                SampleKey.PRIVATE_KEY_STRING);
//
//        assertNotEquals(wallet.verifyPassword("benn"), true);
//        assertEquals(wallet.getAddress(), SampleKey.ADDRESS);
//        wallet.delete(SampleKey.PASSWORD);

//        new Metadata(ChainType.ETHEREUM, Network.MAINNET, "name", "passwordHint");
        Metadata metadata = new Metadata();
        metadata.setChainType(walletType);
        metadata.setNetwork(Network.TESTNET);
        metadata.setName("import-"+walletType);
        metadata.setPasswordHint(hint);
        WalletManager.importWalletFromPrivateKey(metadata,privateKey,pass,true);
    }
}
