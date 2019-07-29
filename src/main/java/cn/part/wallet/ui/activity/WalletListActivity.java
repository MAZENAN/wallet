package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import org.consenlabs.tokencore.wallet.Identity;
import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.ui.adapter.WalletAdapter;

public class WalletListActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rc_wallet_list)
    RecyclerView recyclerView;
    private WalletAdapter adapter;
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
        adapter = new WalletAdapter(Identity.getCurrentIdentity().getWallets());
        adapter.setListener((view,wallet)->{
            Intent intent = new Intent(mContext,WalletManageActivity.class);
            intent.putExtra("wallet_id",wallet.getId());
            startActivity(intent);
        });
    }

    @Override
    public void configViews() {
        tvTitle.setText("钱包管理");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.iv_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_btn:
                launchActivity(WalletAddChooseTypeActivity.class);
                break;
        }
    }
}
