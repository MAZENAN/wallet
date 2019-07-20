package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.entity.Token;
import cn.part.wallet.utils.LogUtils;

public class ProperyDetailActivity extends BaseActivity {
    private Token token;

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_property_detail;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        token = intent.getParcelableExtra("token");
        String walletId = intent.getStringExtra("wallet_id");
        LogUtils.i(ProperyDetailActivity.class.getSimpleName(),token.getTokenName());
        LogUtils.i(ProperyDetailActivity.class.getSimpleName(),walletId);

    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.lly_transfer})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.lly_transfer:
                intent = new Intent(mContext,TransferActivity.class);
                startActivity(intent);
                break;
        }
    }
}
