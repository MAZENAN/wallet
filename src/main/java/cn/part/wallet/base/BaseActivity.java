package cn.part.wallet.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import com.gyf.barlibrary.ImmersionBar;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.part.wallet.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar mCommonToolbar;
    protected Context mContext;
    private Unbinder unbinder;
    protected final String TAG = getClass().getSimpleName();
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        setContentView(getLayoutId());
        mContext = this;
        ImmersionBar.with(this).init();
        unbinder = ButterKnife.bind(this);

        mCommonToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (mCommonToolbar != null) {
            ImmersionBar.with(this)
                    .titleBar(mCommonToolbar, false)
                    .transparentStatusBar()
                    .statusBarDarkFont(true, 1f)
                    .navigationBarColor(R.color.white)
                    .init();
            initToolBar();
            setSupportActionBar(mCommonToolbar);
        }
        LinearLayout llyBack = (LinearLayout) findViewById(R.id.lly_back);
        if (llyBack != null) {
            llyBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        initDatas();
        configViews();

    }

    protected abstract void onBeforeSetContentLayout();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        dismissDialog();
    }

    public abstract int getLayoutId();

    public abstract void initToolBar();

    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void launchActivity(Class<?> activity) {
        if(mContext!=null){
            Intent intent = new Intent(mContext, activity);
            mContext.startActivity(intent);
        }

    }

    public SweetAlertDialog getpDialog(){
        if (pDialog == null){
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#FFFF5454"));
            pDialog.setCancelable(false);
        }
        return pDialog;
    }

    public void showDialog(String title) {
        if (!title.isEmpty()){
            getpDialog().setTitleText(title);
            getpDialog().show();
        }
    }

    public void dismissDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void switchDialog(Boolean bool,String tips) {
        if (bool) {
            showDialog(tips);
        }else {
            dismissDialog();
        }
    }
}
