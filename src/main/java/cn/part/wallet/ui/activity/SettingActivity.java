package cn.part.wallet.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.utils.Conf;
import cn.part.wallet.utils.PrefUtils;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_setting_net)
    TextView mTvNetValue;
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initToolBar() {
        mTvTitle.setText("我的设置");
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        mTvNetValue.setText(PrefUtils.getString(Conf.Net.KEY_NET,Conf.Net.NET_MAIN,mContext));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvNetValue.setText(PrefUtils.getString(Conf.Net.KEY_NET,Conf.Net.NET_MAIN,mContext));
    }

    @OnClick({R.id.rl_coin_setting,R.id.rl_net_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_net_setting:
                launchActivity(NetSettingActivity.class);
                break;
            case R.id.rl_coin_setting:
                launchActivity(CoinSettingActivity.class);
                break;
        }
    }
}
