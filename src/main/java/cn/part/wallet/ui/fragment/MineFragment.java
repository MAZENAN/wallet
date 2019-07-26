package cn.part.wallet.ui.fragment;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.Identity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseFragment;
import cn.part.wallet.ui.activity.AddressListActivity;
import cn.part.wallet.ui.activity.IdentityActivity;
import cn.part.wallet.ui.activity.WalletListActivity;


public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_identity_name)
    TextView tvIdentityName;
    @BindView(R.id.wallet_manage)
    ViewGroup walletManage;
    @BindView(R.id.address_manage)
    ViewGroup addressManage;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        tvIdentityName.setText(Identity.getCurrentIdentity().getMetadata().getName());
    }

    @OnClick({R.id.tv_identity_name,R.id.wallet_manage,R.id.address_manage})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_identity_name:
                intent = new Intent(getActivity(), IdentityActivity.class);
                break;
            case R.id.wallet_manage:
                intent = new Intent(getActivity(), WalletListActivity.class);
                break;
            case R.id.address_manage:
                intent = new Intent(getActivity(), AddressListActivity.class);
                break;
        }
        mContext.startActivity(intent);
    }
}
