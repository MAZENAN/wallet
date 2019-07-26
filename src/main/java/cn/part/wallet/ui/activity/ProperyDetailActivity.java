package cn.part.wallet.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import cn.part.wallet.ui.adapter.HomePagerAdapter;
import cn.part.wallet.ui.adapter.TranListPagerAdapter;
import cn.part.wallet.ui.fragment.HistroyTransFragment;
import cn.part.wallet.ui.fragment.InTransFragment;
import cn.part.wallet.ui.fragment.MineFragment;
import cn.part.wallet.ui.fragment.PropertyFragment;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.view.NoScrollViewPager;
import cn.part.wallet.viewmodel.PropertyDetailViewModel;

public class ProperyDetailActivity extends BaseActivity {
    private Token token;
//    private PropertyDetailViewModel detailViewModel;
//    private EthTxAdapter adapter;
//    @BindView(R.id.rc_tx_list)
//    RecyclerView rcTxList;
//    @BindView(R.id.refresh_layout)
//    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.mytab)
    TabLayout tabLayout;
    @BindView(R.id.vp_trans_list)
    NoScrollViewPager vpTrans;

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
//        detailViewModel = ViewModelProviders.of(this).get(PropertyDetailViewModel.class);
//        detailViewModel.getEthTxList(token.getAddress()).observe(this,this::onTxListGet);
//        detailViewModel.getLoadind().observe(this,this::onLoading);
    }

    private void onLoading(Boolean bool) {
//        refreshLayout.setRefreshing(bool);
    }

//    private void onTxListGet(EthTxInfo ethTxInfo) {
//        detailViewModel.setLoading(false);
//        if (ethTxInfo != null){
//            List<TransInfo> list = ethTxInfo.getResult();
//            adapter.setList(list);
//        }
//    }

    @Override
    public void configViews() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        //设置布局管理器
//        rcTxList.setLayoutManager(linearLayoutManager);
//        adapter = new EthTxAdapter(new ArrayList<>(),token.getAddress());
//        adapter.setListener(new EthTxAdapter.TxItemClickListener() {
//            @Override
//            public void itemClick(View view, TransInfo resultBean) {
//                Intent intent = new Intent(mContext,TxDetailActivity.class);
//                intent.putExtra("transInfo",resultBean);
//                startActivity(intent);
//            }
//        });
//        rcTxList.setAdapter(adapter);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                detailViewModel.refreshTxList(token.getAddress());
//            }
//        });


//        tabLayout.addTab(tabLayout.newTab().setText("交易中"));
//        tabLayout.addTab(tabLayout.newTab().setText("历史"));





        vpTrans.setOffscreenPageLimit(2);
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("交易中");
        titleList.add("历史交易");

        fragmentList.add(InTransFragment.newInstance(token.getAddress()));
        fragmentList.add(new HistroyTransFragment());
        TranListPagerAdapter tranListPagerAdapter = new TranListPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        vpTrans.setAdapter(tranListPagerAdapter);
        vpTrans.setCurrentItem(0, false);
        tabLayout.setupWithViewPager(vpTrans);
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
