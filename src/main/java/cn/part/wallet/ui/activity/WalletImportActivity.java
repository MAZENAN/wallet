package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import org.consenlabs.tokencore.wallet.model.ChainType;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.ui.adapter.TranListPagerAdapter;
import cn.part.wallet.ui.fragment.ImportWalletByHdFragment;
import cn.part.wallet.ui.fragment.ImportWalletByKeystoreFragment;
import cn.part.wallet.ui.fragment.ImportWalletByMnemonicFragment;
import cn.part.wallet.ui.fragment.ImportWalletByPrivatekeyFragment;

public class WalletImportActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mytab)
    TabLayout mTab;
    @BindView(R.id.vp_import)
    ViewPager vpImport;

    private String type;
    private List<String> mListTitle;
    private List<Fragment> mFragmentList;
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_import_wallet;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        type = intent.getStringExtra("wallet_type");
        mListTitle = new ArrayList<>();
        mFragmentList = new ArrayList<>();
        switch (type) {
            case ChainType.ETHEREUM:
                mListTitle.add("Keystore");
                mListTitle.add("助记词");
                mListTitle.add("私钥");
                mListTitle.add("冷钱包");
                mFragmentList.add(ImportWalletByKeystoreFragment.newInstance(type));
                mFragmentList.add(ImportWalletByMnemonicFragment.newInstance(type));
                mFragmentList.add(ImportWalletByPrivatekeyFragment.newInstance(type));
                mFragmentList.add(ImportWalletByHdFragment.newInstance(type));
                break;
            case ChainType.BITCOIN:
                mListTitle.add("助记词");
                mListTitle.add("私钥");
                mFragmentList.add(ImportWalletByMnemonicFragment.newInstance(type));
                mFragmentList.add(ImportWalletByPrivatekeyFragment.newInstance(type));
                break;
            case ChainType.EOS:
                mListTitle.add("私钥");
                mListTitle.add("助记词");
                mListTitle.add("ETH私钥");
                mFragmentList.add(ImportWalletByPrivatekeyFragment.newInstance(type));
                mFragmentList.add(ImportWalletByMnemonicFragment.newInstance(type));
                mFragmentList.add(ImportWalletByPrivatekeyFragment.newInstance(type));
                break;
        }
    }

    @Override
    public void configViews() {
        tvTitle.setText("导入 " + type + " 钱包");
        vpImport.setOffscreenPageLimit(4);
        TranListPagerAdapter tranListPagerAdapter = new TranListPagerAdapter(getSupportFragmentManager(), mFragmentList, mListTitle);
        vpImport.setAdapter(tranListPagerAdapter);
        vpImport.setCurrentItem(0, false);
        mTab.setupWithViewPager(vpImport);
    }
}
