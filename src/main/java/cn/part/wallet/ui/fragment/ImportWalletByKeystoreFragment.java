package cn.part.wallet.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class ImportWalletByKeystoreFragment extends NoBarBaseFragment {
    @BindView(R.id.et_keystore)
    EditText etKeystore;
    @BindView(R.id.et_wallet_pwd)
    EditText etPwd;
    @BindView(R.id.btn_import_wallet)
    TextView btnBegin;

    private static final String WALLET_TYPE = "wallet_type";
    private String walletType;
    private String keystore;
    private String pass;

    public static ImportWalletByKeystoreFragment newInstance(String param) {
        ImportWalletByKeystoreFragment fragment = new ImportWalletByKeystoreFragment();
        Bundle args = new Bundle();
        args.putString(WALLET_TYPE, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_import_wallet_by_keystore;
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
                        LogUtils.i(getTag(),"开始导入"+walletType +"钱包");
                        importWalletByKeyStore();
                    }catch (Exception e) {
                        LogUtils.e(getTag(),e.getMessage());
                    }

                }
                break;
        }
    }

    @OnTextChanged({R.id.et_keystore,R.id.et_wallet_pwd})
    public void onTextChange(CharSequence text) {
        keystore = getEdtStr(etKeystore);
        pass = getEdtStr(etPwd);

        boolean isEnable = !TextUtils.isEmpty(keystore)&&!TextUtils.isEmpty(pass) ? true : false;
        btnBegin.setEnabled(isEnable);
    }

    private String getEdtStr(EditText editText){
        return editText.getText().toString().trim();
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(keystore)){
            ToastUtil.showToast(R.string.import_wallet_keystore_input_tips);
            return false;
        }
        if (TextUtils.isEmpty(pass)){
            ToastUtil.showToast(R.string.import_wallet_pwd_input_tips);
            return false;
        }
        return true;
    }

  private void importWalletByKeyStore(){
      Metadata metadata = new Metadata();
      metadata.setChainType(walletType);
      metadata.setNetwork(Network.MAINNET);
      metadata.setName("import-"+walletType);
//      new Metadata(ChainType.ETHEREUM, Network.MAINNET, "name", "passwordHint")
      Wallet wallet = WalletManager.importWalletFromKeystore(metadata,keystore, pass, true);
  }
}
