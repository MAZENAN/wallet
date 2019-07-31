package cn.part.wallet.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import org.consenlabs.tokencore.wallet.Identity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseFragment;
import cn.part.wallet.ui.activity.AddressListActivity;
import cn.part.wallet.ui.activity.GuideActivity;
import cn.part.wallet.ui.activity.IdentityActivity;
import cn.part.wallet.ui.activity.WalletListActivity;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.view.PwdInputAlertDialog;
import cn.part.wallet.viewmodel.GlobalOpViewModel;

public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_identity_name)
    TextView tvIdentityName;
    GlobalOpViewModel mViewModel;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void attachView() { }

    @Override
    public void initDatas() {
        mViewModel = ViewModelProviders.of(this).get(GlobalOpViewModel.class);
        mViewModel.getLoadingData().observe(this,this::onLoading);
        mViewModel.getDeleteResultData().observe(this,this::onQuitIdentity);
    }

    private void onQuitIdentity(Boolean bool) {
        if (bool) {
            Intent intent = new Intent(mContext, GuideActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mContext.startActivity(intent);
        }else {
            ToastUtil.showToast("密码错误");
        }
    }

    private void onLoading(Boolean bool) {
        switchDialog(bool,"正在退出...");
    }

    @Override
    public void configViews() {
        tvIdentityName.setText(Identity.getCurrentIdentity().getMetadata().getName());
    }

    @OnClick({R.id.tv_identity_name,R.id.rl_wallet_manage,R.id.rl_address_manage,R.id.tv_quit_identity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_identity_name:
                launchActivity(IdentityActivity.class);
                break;
            case R.id.rl_wallet_manage:
                launchActivity(WalletListActivity.class);
                break;
            case R.id.rl_address_manage:
               // launchActivity(AddressListActivity.class);
                break;
            case R.id.tv_quit_identity:
                showPwdInputDialog();
                break;
        }
    }

    /**
     * 退出身份弹框
     */
    private void showPwdInputDialog() {
        PwdInputAlertDialog dialog = new PwdInputAlertDialog(mContext);
        dialog.setOnOkClick((pwd)->{
            dialog.dismiss();
            mViewModel.postDeleteIdentity(pwd);
        });
        dialog.show();
    }
}
