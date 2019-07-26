package cn.part.wallet.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseFragment;

public class HistroyTransFragment extends BaseFragment {
    @BindView(R.id.rc_tx_list)
    RecyclerView rcTxList;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_trans_histroy_layout;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }
}
