package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.consenlabs.tokencore.wallet.model.ChainType;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;

public class WalletManageActivity extends BaseActivity {
    @BindView(R.id.ll_export_mnemonic)
    LinearLayout ll_export_mnemonic;
    @BindView(R.id.ll_export_keystore)
    LinearLayout ll_export_keystore;
    @BindView(R.id.ll_export_privateKey)
    LinearLayout ll_export_privateKey;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Wallet mWallet;
    private String mWalletId;
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_manage_list;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("管理");
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        mWalletId = intent.getStringExtra("wallet_id");
        mWallet = WalletManager.mustFindWalletById(mWalletId);
    }

    @Override
    public void configViews() {
        String chainType = mWallet.getMetadata().getChainType();
        if (chainType.equals(ChainType.EOS)||chainType.equals(ChainType.BITCOIN)) {
            ll_export_keystore.setVisibility(View.GONE);
            ll_export_privateKey.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_export_mnemonic,R.id.ll_export_keystore,R.id.ll_export_privateKey})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_export_mnemonic:
                try{
                    LogUtils.e(TAG,"钱包id"+mWalletId);
                    String mnemonic = WalletManager.exportMnemonic(mWalletId, "123456").getMnemonic();
                    ToastUtil.showToast(mnemonic);
                }catch (Exception e){
                    LogUtils.e(TAG,"导出失败" + e.getMessage());
                }

                break;
            case R.id.ll_export_privateKey:
                String s = WalletManager.exportPrivateKey(mWalletId, "123456");
                LogUtils.i(TAG,s);
                ToastUtil.showToast(s);
                break;
            case R.id.ll_export_keystore:
                try{
                    String s1 = WalletManager.exportKeystore(mWalletId, "123456");
                    ToastUtil.showToast(s1);
                }catch (Exception e) {
                    LogUtils.e(TAG,"导出keystore失败"+e);
                }
                break;
        }
    }
}
