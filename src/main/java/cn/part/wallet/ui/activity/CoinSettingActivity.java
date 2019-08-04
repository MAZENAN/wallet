package cn.part.wallet.ui.activity;

import android.widget.TextView;

import butterknife.BindView;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;

public class CoinSettingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onBeforeSetContentLayout() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coin_setting;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        tvTitle.setText("货币单位");
    }
}
