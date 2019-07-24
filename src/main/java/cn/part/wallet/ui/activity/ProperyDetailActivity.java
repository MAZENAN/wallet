package cn.part.wallet.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.entity.Token;
import cn.part.wallet.entity.TransInfo;
import cn.part.wallet.service.response.EthTxInfo;
import cn.part.wallet.ui.adapter.EthTxAdapter;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.viewmodel.PropertyDetailViewModel;

public class ProperyDetailActivity extends BaseActivity {
    private Token token;
    private PropertyDetailViewModel detailViewModel;
    private EthTxAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rc_tx_list)
    RecyclerView rcTxList;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.mytab)
    TabLayout tabLayout;
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_property_detail;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        token = intent.getParcelableExtra("token");
        String walletId = intent.getStringExtra("wallet_id");
        LogUtils.i(ProperyDetailActivity.class.getSimpleName(),token.getTokenName());
        LogUtils.i(ProperyDetailActivity.class.getSimpleName(),walletId);
        detailViewModel = ViewModelProviders.of(this).get(PropertyDetailViewModel.class);
        detailViewModel.getEthTxList(token.getAddress()).observe(this,this::onTxListGet);
        detailViewModel.getLoadind().observe(this,this::onLoading);
    }

    private void onLoading(Boolean bool) {
        refreshLayout.setRefreshing(bool);
    }

    private void onTxListGet(EthTxInfo ethTxInfo) {
        detailViewModel.setLoading(false);
        if (ethTxInfo != null){
            List<TransInfo> list = ethTxInfo.getResult();
            adapter.setList(list);
        }
    }

    @Override
    public void configViews() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //设置布局管理器
        rcTxList.setLayoutManager(linearLayoutManager);
        adapter = new EthTxAdapter(new ArrayList<>(),token.getAddress());
        adapter.setListener(new EthTxAdapter.TxItemClickListener() {
            @Override
            public void itemClick(View view, TransInfo resultBean) {
                Intent intent = new Intent(mContext,TxDetailActivity.class);
                intent.putExtra("transInfo",resultBean);
                startActivity(intent);
            }
        });
        rcTxList.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                detailViewModel.refreshTxList(token.getAddress());
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("交易中"));
        tabLayout.addTab(tabLayout.newTab().setText("历史"));
    }

    @OnClick({R.id.lly_transfer})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.lly_transfer:
                intent = new Intent(mContext,TransferActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
                break;
        }
    }
}
