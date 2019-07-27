package cn.part.wallet.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gyf.barlibrary.ImmersionBar;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.model.ChainType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseFragment;
import cn.part.wallet.entity.Token;
import cn.part.wallet.ui.activity.ProperyDetailActivity;
import cn.part.wallet.ui.activity.QRCodeScannerActivity;
import cn.part.wallet.ui.activity.ReceiptActivity;
import cn.part.wallet.ui.adapter.SwitchWalletAdapter;
import cn.part.wallet.ui.adapter.TokenAdapter;
import cn.part.wallet.viewmodel.ProperyViewModel;

public class PropertyFragment extends BaseFragment {
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swf_property)
    SwipeRefreshLayout refresh;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rc_token)
    RecyclerView rcProperty;//资产Token列表
    @BindView(R.id.tv_main_wallet_name)
    TextView tvWalletName;
    @BindView(R.id.tv_wallet_main_addr)
    TextView tvWalletAddress;
    @BindView(R.id.switch_menu)
    ImageView opMenu;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.iv_btn)
    ImageView qrBtn;

    @BindView(R.id.ll_nav)
    LinearLayout llNav;
    @BindView(R.id.rc_wallet_list)
    RecyclerView rcWallets;

    private ProperyViewModel viewModel;
    private TokenAdapter tokenAdapter;
    private SwitchWalletAdapter switchWalletAdapter;
    private Wallet currentWallet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_property;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        viewModel = ViewModelProviders.of(this).get(ProperyViewModel.class);

        tokenAdapter = new TokenAdapter(new ArrayList<>());
        tokenAdapter.setItemClickListener(this::onTokenItemClick);

        switchWalletAdapter = new SwitchWalletAdapter(new ArrayList<>());
        switchWalletAdapter.setItemClickListener(this::onWalletItemClick);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void configViews() {
        tvTitle.setText(R.string.title_main_wallet);
        refresh.setProgressBackgroundColorSchemeColor(Color.parseColor("#F5F5F5"));

        rcProperty.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcProperty.setAdapter(tokenAdapter);
        rcWallets.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcWallets.setAdapter(switchWalletAdapter);

        viewModel.getDefaultWallet().observe(this,this::refreshViews);
        viewModel.getWalletList().observe(this,this::onLoadWallets);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        return super.onCreateView(inflater, container, state);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @OnClick({R.id.btn_manage_wallet,R.id.tv_wallet_main_addr,R.id.switch_menu,R.id.iv_btn})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.tv_wallet_main_addr:
                intent = new Intent(getActivity(), ReceiptActivity.class);
                intent.putExtra("wallet_id",currentWallet.getId());
                startActivity(intent);
                break;
            case R.id.switch_menu:
                drawer.openDrawer(llNav);
                break;
            case R.id.iv_btn:
                intent = new Intent(getActivity(), QRCodeScannerActivity.class);
                startActivity(intent);
        }
    }

    /**
     * 根据默认钱包刷新加载主页面数据
     * @param wallet
     */
    private void refreshViews(Wallet wallet) {
        currentWallet = wallet;

        String address = wallet.getMetadata().getChainType().equals(ChainType.ETHEREUM) ? "0x"+wallet.getAddress() : wallet.getAddress();
        tvWalletAddress.setText(address);
        tvWalletName.setText(wallet.getMetadata().getName());

        //刷新token列表
        List<Token> list = new ArrayList<>();
        addDefaultToken(list);
        tokenAdapter.setList(list);
    }

    /**
     * 添加默认Token
     * @param list
     */
    private void addDefaultToken(List<Token> list) {
        switch(currentWallet.getMetadata().getChainType()) {
            case ChainType.ETHEREUM:
                list.add(new Token("ETH",new BigDecimal("0"),new BigDecimal("0"),"0x"+currentWallet.getAddress(),"",ChainType.ETHEREUM,false));
                break;
            case ChainType.BITCOIN:
                list.add(new Token("BTC",new BigDecimal("0"),new BigDecimal("0"),currentWallet.getAddress(),"",ChainType.BITCOIN,false));
                list.add(new Token("USDT",new BigDecimal("0"),new BigDecimal("0"),currentWallet.getAddress(),"",ChainType.BITCOIN,true));
                break;
        }
    }

    /**
     * 加载钱包列表
     * @param wallets
     */
    private void onLoadWallets(List<Wallet> wallets) {
        switchWalletAdapter.setmList(wallets);
    }

    /**
     * Token item点击
     * @param view
     * @param token
     */
    private void onTokenItemClick(View view, Token token) {
        Intent intent = new Intent(getActivity(), ProperyDetailActivity.class);
        intent.putExtra("wallet_id",currentWallet.getId());
        intent.putExtra("token",token);
        intent.putExtra("tokenNum",token.getTokenNum());
        startActivity(intent);
    }

    /**
     * wallet item 点击切换默认钱包
     * @param view
     * @param wallet
     */
    private void onWalletItemClick(View view, Wallet wallet) {
        if (currentWallet!=null && !wallet.getAddress().equals(currentWallet.getAddress()))
            viewModel.setDefaultWallet(wallet);
        drawer.closeDrawer(llNav);
    }
}
