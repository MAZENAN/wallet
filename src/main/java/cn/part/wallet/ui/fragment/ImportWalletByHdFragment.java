package cn.part.wallet.ui.fragment;

import android.os.Bundle;

import cn.part.wallet.R;
import cn.part.wallet.base.BaseFragment;
import cn.part.wallet.base.NoBarBaseFragment;

public class ImportWalletByHdFragment extends NoBarBaseFragment {
    private static final String WALLET_TYPE = "wallet_type";
    private String walletType;

    public static ImportWalletByHdFragment newInstance(String param) {
        ImportWalletByHdFragment fragment = new ImportWalletByHdFragment();
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
}
