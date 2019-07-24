package cn.part.wallet.ui.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseFragment;
import cn.part.wallet.entity.Token;
import cn.part.wallet.entity.WalletInfo;
import cn.part.wallet.ui.activity.ProperyDetailActivity;
import cn.part.wallet.ui.activity.QRCodeScannerActivity;
import cn.part.wallet.ui.activity.ReceiptActivity;
import cn.part.wallet.ui.adapter.TokenAdapter;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.viewmodel.ProperyViewModel;

public class PropertyFragment extends BaseFragment {
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipeRefresh_property)
    SwipeRefreshLayout refresh;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rc_property)
    RecyclerView rcProperty;
    @BindView(R.id.tv_main_wallet_name)
    TextView walletName;
    @BindView(R.id.tv_wallet_main_addr)
    TextView walletAddress;
    @BindView(R.id.nav_view)
    NavigationView nav;
    @BindView(R.id.switch_menu)
    ImageView opMenu;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.iv_btn)
    ImageView qrBtn;
    Identity currentIdentity ;
    private ProperyViewModel viewModel;
    private WalletInfo currentWalletInfo;
    private LinearLayoutManager linearLayoutManager;
    private int bannerHeight = 300;
    private TokenAdapter adapter;
    private static final int QRCODE_SCANNER_REQUEST = 1100;
    private static final int CREATE_WALLET_REQUEST = 1101;
    private static final int ADD_NEW_PROPERTY_REQUEST = 1102;
    private static final int WALLET_DETAIL_REQUEST = 1104;

    private String currency = "CNY";

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
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //设置布局管理器
        rcProperty.setLayoutManager(linearLayoutManager);
        viewModel = ViewModelProviders.of(this).get(ProperyViewModel.class);
        currentIdentity = Identity.getCurrentIdentity();
        currentWalletInfo = (WalletInfo) viewModel.getLikedData().getValue();
        List<Token> list = new ArrayList<>();
        Token token = new Token("ETH",new BigDecimal(0),new BigDecimal(0),currentWalletInfo.getAddress(),"");
        list.add(token);
        adapter = new TokenAdapter(list);
        rcProperty.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void configViews() {

        tvTitle.setText(R.string.title_main_wallet);
        refresh.setProgressBackgroundColorSchemeColor(Color.parseColor("#F5F5F5"));

        walletName.setText(currentWalletInfo.getWalletName());
        String addr = currentWalletInfo.getAddress().substring(0,10) + "..."+ currentWalletInfo.getAddress().substring(30);
        walletAddress.setText(addr);
        initNav();
        adapter.setItemClickListener((view,token)->{
            Intent intent = new Intent(getActivity(), ProperyDetailActivity.class);

            intent.putExtra("wallet_id",currentWalletInfo.getId());
            intent.putExtra("token",token);
            intent.putExtra("tokenNum",token.getTokenNum());
            startActivity(intent);
        });
    }

    private void initNav() {
        ((TextView)nav.getHeaderView(0).findViewById(R.id.tv_identity_name)).setText(currentIdentity.getMetadata().getName());
        Menu menu = nav.getMenu();
        List<Wallet> wallets = currentIdentity.getWallets();
        for (Wallet wallet:wallets) {
            menu.add(wallet.getAddress());
        }

        for (int i=0;i<wallets.size();i++) {
            MenuItem item = menu.getItem(i);
            item.setIcon(R.drawable.avator_normal);
        }
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
                intent.putExtra("wallet_id",currentWalletInfo.getId());
                startActivity(intent);
                break;
            case R.id.switch_menu:
                drawer.openDrawer(nav);
                break;
            case R.id.iv_btn:
                intent = new Intent(getActivity(), QRCodeScannerActivity.class);
                startActivity(intent);
        }
    }
}
