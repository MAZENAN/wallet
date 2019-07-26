package cn.part.wallet.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.model.ChainType;

import butterknife.BindView;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.entity.AddressBook;
import cn.part.wallet.utils.ToastUtil;

public class AddressAddActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_btn)
    TextView btnSave;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.et_address)
    EditText etAddress;

    private String name;
    private String address;
    private String remark;

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_add;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("新建联系人");
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        btnSave.setOnClickListener(this::addressSave);
    }

    private void addressSave(View view) {

        AddressBook addressBook = new AddressBook();
        addressBook.setName(etName.getText().toString().trim());
        addressBook.setAddress(etAddress.getText().toString().trim());
        addressBook.setRemark(etRemark.getText().toString().trim());
        addressBook.setType(ChainType.ETHEREUM);
        boolean save = addressBook.save();
        if (save) {
            ToastUtil.showToast("添加成功");
        }else {
            ToastUtil.showToast("添加失败");
        }
    }
}
