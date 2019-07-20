package cn.part.wallet.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.ui.adapter.WalletAdapter;
import cn.part.wallet.viewmodel.WalletViewModel;

public class WalletListActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rc_wallet_list)
    RecyclerView recyclerView;
    private WalletAdapter adapter;
    private WalletViewModel viewModel;
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        viewModel = ViewModelProviders.of(this).get(WalletViewModel.class);
        adapter = new WalletAdapter(viewModel.getWalletListLiveData().getValue());
    }

    @Override
    public void configViews() {
        tvTitle.setText(R.string.mine_wallet_manage);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
