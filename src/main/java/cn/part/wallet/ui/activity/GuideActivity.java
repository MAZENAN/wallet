package cn.part.wallet.ui.activity;

import android.view.View;
import android.widget.Button;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
public class GuideActivity extends BaseActivity {
    @BindView(R.id.btn_create_identity)
    Button btnCreate;
    @BindView(R.id.btn_recovery_identity)
    Button btnRecovery;

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initToolBar() {
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
    }

    @OnClick({R.id.btn_create_identity,R.id.btn_recovery_identity})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_create_identity:
                    launchActivity(IdentityAddActivity.class);
                break;
            case R.id.btn_recovery_identity:
                launchActivity(IdentityRecoveryActivity.class);
                break;
        }
    }
}
