package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.model.ChainType;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.view.WalletOpPopWindow;

public class WalletAddChooseTypeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private WalletOpPopWindow opPopWindow;

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_add_choose_type;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("添加钱包");
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.ll_wallet_eth,R.id.ll_wallet_btc,R.id.ll_wallet_eos})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wallet_eth:
                showPopWindow(ChainType.ETHEREUM);
                break;
            case R.id.ll_wallet_btc:
                showPopWindow(ChainType.BITCOIN);
                break;
            case R.id.ll_wallet_eos:
                showPopWindow(ChainType.EOS);
                break;
        }
    }

    private void showPopWindow(String type) {
        opPopWindow = new WalletOpPopWindow(mContext, new WalletOpPopWindow.walletOpListener() {
            @Override
            public void walletCreate() {
                closePopWindow();
                Intent intent = new Intent(mContext,WalletAddActivity.class);
                intent.putExtra("wallet_type",type);
                startActivity(intent);
            }

            @Override
            public void walletImport() {
                closePopWindow();
                Intent intent = new Intent(mContext, WalletImportActivity.class);
                intent.putExtra("wallet_type",type);
                startActivity(intent);
            }

            @Override
            public void cancle() {
                closePopWindow();
            }
        });
        opPopWindow.showAsDropDown(mCommonToolbar);
    }

    private void closePopWindow() {
        if (opPopWindow != null) {
            opPopWindow.dismiss();
        }
    }
}
