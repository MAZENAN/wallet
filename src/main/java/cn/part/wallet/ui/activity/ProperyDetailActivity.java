package cn.part.wallet.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import cn.part.wallet.entity.TxInfo;
import cn.part.wallet.ui.adapter.TxlistAdapter;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.viewmodel.PropertyDetailViewModel;

public class ProperyDetailActivity extends BaseActivity {
    @BindView(R.id.rc_tx_list)
    RecyclerView rcTxList;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private Token token;
    private PropertyDetailViewModel detailViewModel;
    private TxlistAdapter txlistAdapter;

    @Override
    protected void onBeforeSetContentLayout() {}

    @Override
    public int getLayoutId() {
        return R.layout.activity_property_detail;
    }

    @Override
    public void initToolBar() {}

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        token = intent.getParcelableExtra("token");
        LogUtils.i(ProperyDetailActivity.class.getSimpleName(),token.getTokenName());
        detailViewModel = ViewModelProviders.of(this).get(PropertyDetailViewModel.class);

        txlistAdapter = new TxlistAdapter(new ArrayList<>());
        txlistAdapter.setListener(this::onTxitemClick);

        detailViewModel.getETxList(token).observe(this,this::onTxListGet);
        detailViewModel.getLoadind().observe(this,this::onLoading);
    }

    private void onTxListGet(List<TxInfo> txInfos) {
        detailViewModel.setLoading(false);
        if (txInfos != null){
            txlistAdapter.setList(txInfos);
        }
    }

    private void onTxitemClick(View view, TxInfo txInfo) {
//        Intent intent = new Intent(mContext,TxDetailActivity.class);
//        intent.putExtra("transInfo",resultBean);
//        startActivity(intent);
    }

    private void onLoading(Boolean bool) {
        refreshLayout.setRefreshing(bool);
    }

    @Override
    public void configViews() {
        rcTxList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcTxList.setAdapter(txlistAdapter);
        refreshLayout.setOnRefreshListener(this::onRefresh);
    }

    private void onRefresh() {
        detailViewModel.refreshTxList(token);
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
