package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import org.consenlabs.tokencore.wallet.Identity;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.utils.MyThreadPool;

public class SplashActivity extends BaseActivity {
    private static final int DELAY_MILLIS = 2000;

    @Override
    protected void onBeforeSetContentLayout() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_splash;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
    }

    @Override
    public void configViews() {
        MyThreadPool.runOnUiThreadDelayed(this::chooseActivity, DELAY_MILLIS);
    }

    private void chooseActivity() {
        Identity identity = Identity.getCurrentIdentity();
        Intent intent = identity == null ? new Intent(SplashActivity.this,GuideActivity.class): new Intent(SplashActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SplashActivity.this.startActivity(intent);
    }

    @Override
    protected Boolean onPressBack() {
        return true;
    }
}
