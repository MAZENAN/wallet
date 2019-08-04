package cn.part.wallet.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.utils.Conf;
import cn.part.wallet.utils.PrefUtils;
import cn.part.wallet.utils.ToastUtil;

public class NetSettingActivity  extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_test_check)
    ImageView imgTest;
    @BindView(R.id.img_main_check)
    ImageView imgMain;

    private String mCurrentNet;

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_net_setting;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        mCurrentNet = PrefUtils.getString(Conf.Net.KEY_NET,Conf.Net.NET_MAIN,mContext);
    }

    @Override
    public void configViews() {
        tvTitle.setText("网络环境");
        initCheck();
    }

    private void initCheck() {
        imgMain.setVisibility(mCurrentNet.equals(Conf.Net.NET_MAIN) ? View.VISIBLE : View.INVISIBLE);
        imgTest.setVisibility(mCurrentNet.equals(Conf.Net.NET_TEST) ? View.VISIBLE : View.INVISIBLE);
    }

    @OnClick({R.id.btn_save,R.id.rl_net_main,R.id.rl_net_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_net_main:
                mCurrentNet = Conf.Net.NET_MAIN;
                initCheck();
                break;
            case R.id.rl_net_test:
                mCurrentNet = Conf.Net.NET_TEST;
                initCheck();
                break;
            case R.id.btn_save:
                PrefUtils.putString(Conf.Net.KEY_NET,mCurrentNet,mContext);
                ToastUtil.showToast("保存成功");
                finish();
                break;
        }
    }
}
