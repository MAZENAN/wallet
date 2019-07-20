package cn.part.wallet.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.widget.TextView;

import butterknife.BindView;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.entity.IdentityInfo;
import cn.part.wallet.viewmodel.IdentityViewModel;

public class IdentityActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_identity_name)
    TextView tvName;
    @BindView(R.id.tv_identity_id)
    TextView tvID;
    private IdentityViewModel viewModel;
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_identity;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        viewModel = ViewModelProviders.of(this).get(IdentityViewModel.class);
    }

    @Override
    public void configViews() {
        tvTitle.setText(R.string.identity_title);
        IdentityInfo identityInfo = (IdentityInfo) viewModel.getLikedData().getValue();
        tvName.setText(identityInfo.getName());
        String ID = identityInfo.getID();

        tvID.setText(ID);
    }
}
