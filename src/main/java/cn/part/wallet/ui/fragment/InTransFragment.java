package cn.part.wallet.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseFragment;
import cn.part.wallet.entity.TransInfo;
import cn.part.wallet.service.response.EthTxInfo;
import cn.part.wallet.ui.activity.TxDetailActivity;
import cn.part.wallet.ui.adapter.EthTxAdapter;
import cn.part.wallet.viewmodel.PropertyDetailViewModel;

public class InTransFragment extends BaseFragment {
    private static final String ARG_Address = "address";

    private EthTxAdapter mEthTxAdapter;
    private String address;
    private PropertyDetailViewModel detailViewModel;
    @BindView(R.id.rc_tx_list)
    RecyclerView rcTxList;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwRefresh;
    public static InTransFragment newInstance(String param) {
        InTransFragment fragment = new InTransFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Address, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_intrans_layout;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        Bundle bundle = getArguments();
        if (bundle != null){
            address = bundle.getString(ARG_Address);
        }
        detailViewModel = ViewModelProviders.of(this).get(PropertyDetailViewModel.class);
//        detailViewModel.getEthTxList(address).observe(this,this::onTxListGet);
        detailViewModel.getLoading().observe(this,this::onLoading);
    }

    @Override
    public void configViews() {
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //设置布局管理器
        rcTxList.setLayoutManager(linearLayoutManager);
        mEthTxAdapter = new EthTxAdapter(new ArrayList<>(),address);
        mEthTxAdapter.setListener(new EthTxAdapter.TxItemClickListener() {
            @Override
            public void itemClick(View view, TransInfo resultBean) {
                Intent intent = new Intent(mContext, TxDetailActivity.class);
                intent.putExtra("transInfo",resultBean);
                startActivity(intent);
            }
        });
        rcTxList.setAdapter(mEthTxAdapter);
        mSwRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //detailViewModel.refreshTxList(address);
            }
        });
    }

    private void onTxListGet(EthTxInfo ethTxInfo) {
        detailViewModel.setLoading(false);
        if (ethTxInfo != null){
            List<TransInfo> list = ethTxInfo.getResult();
            mEthTxAdapter.setList(list);
        }
    }

    private void onLoading(Boolean bool) {
        mSwRefresh.setRefreshing(bool);
    }
}
