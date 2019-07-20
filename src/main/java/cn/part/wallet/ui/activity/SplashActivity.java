package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import org.consenlabs.tokencore.wallet.WalletManager;

import java.io.File;

import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;

import cn.part.wallet.utils.MyThreadPool;
import cn.part.wallet.utils.ToastUtil;

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
        chooseActivity();
    }

    private void chooseActivity() {
//读取本地有无钱包，没有就跳转到guide，有就跳转到main
        MyThreadPool.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SplashActivity.this.startActivity(intent);
            }
        }, DELAY_MILLIS);
    }
}
