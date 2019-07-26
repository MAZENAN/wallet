package cn.part.wallet.ui.activity;

import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.entity.AddressBook;
import cn.part.wallet.utils.ToastUtil;

public class AddressListActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_btn)
    TextView tvAdd;
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("地址本");
    }

    @Override
    public void initDatas() {
        List<AddressBook> all = LitePal.findAll(AddressBook.class);
        for (AddressBook addressbook:all
             ) {
            ToastUtil.showToast(addressbook.getAddress());
        }
    }

    @Override
    public void configViews() {
        tvAdd.setOnClickListener(this::toAddActivity);
    }

    private void toAddActivity(View view) {
        launchActivity(AddressAddActivity.class);
    }
}
