package cn.part.wallet.ui.fragment;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.consenlabs.tokencore.foundation.utils.NumericUtil;
import org.consenlabs.tokencore.wallet.Identity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseFragment;
import cn.part.wallet.ui.activity.AddressListActivity;
import cn.part.wallet.ui.activity.IdentityActivity;
import cn.part.wallet.ui.activity.WalletListActivity;
import cn.part.wallet.utils.LogUtils;


public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_identity_name)
    TextView tvIdentityName;
    @BindView(R.id.wallet_manage)
    ViewGroup walletManage;
    @BindView(R.id.address_manage)
    ViewGroup addressManage;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        String data = "[{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"name\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bytes32\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_spender\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"approve\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"success\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"recovered\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"address\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"totalSupply\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"txGasPriceLimit\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[{\\\"name\\\":\\\"_name\\\",\\\"type\\\":\\\"bytes32\\\"}],\\\"name\\\":\\\"getAddress\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"address\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_from\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_to\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"transferFrom\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"success\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[],\\\"name\\\":\\\"updateRefundGas\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"feeAddr\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"address\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_treasury\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_txGasPriceLimit\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"setupTreasury\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"absMaxFee\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"refundGas\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_absMinFee\\\",\\\"type\\\":\\\"uint256\\\"},{\\\"name\\\":\\\"_feePercent\\\",\\\"type\\\":\\\"uint256\\\"},{\\\"name\\\":\\\"_absMaxFee\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"setFee\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_to\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"},{\\\"name\\\":\\\"_totalSupply\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"issueCoin\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[{\\\"name\\\":\\\"_account\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"balanceOf\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_ambi\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_name\\\",\\\"type\\\":\\\"bytes32\\\"}],\\\"name\\\":\\\"setAmbiAddress\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"feePercent\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_to\\\",\\\"type\\\":\\\"address[]\\\"},{\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256[]\\\"}],\\\"name\\\":\\\"batchTransfer\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_transfer\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"setOperationsCallGas\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[{\\\"name\\\":\\\"_amount\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"calculateFee\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[],\\\"name\\\":\\\"remove\\\",\\\"outputs\\\":[],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_to\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"transfer\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_from\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_to\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"transferPool\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"absMinFee\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"recoveredIndex\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_feeAddr\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"setFeeAddr\\\",\\\"outputs\\\":[],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"transferCallGas\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_old\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_new\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"recoverAccount\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[{\\\"name\\\":\\\"_owner\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_spender\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"allowance\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"remaining\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_to\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_amount\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"rewardTo\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_spender\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"unapprove\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"success\\\",\\\"type\\\":\\\"bool\\\"}],\\\"type\\\":\\\"function\\\"},{\\\"inputs\\\":[],\\\"type\\\":\\\"constructor\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[{\\\"indexed\\\":true,\\\"name\\\":\\\"code\\\",\\\"type\\\":\\\"uint8\\\"},{\\\"indexed\\\":true,\\\"name\\\":\\\"origin\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":true,\\\"name\\\":\\\"sender\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"Error\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[{\\\"indexed\\\":true,\\\"name\\\":\\\"_from\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":true,\\\"name\\\":\\\"_to\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":false,\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"Transfer\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[{\\\"indexed\\\":true,\\\"name\\\":\\\"_owner\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":true,\\\"name\\\":\\\"_spender\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":false,\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"Approved\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[{\\\"indexed\\\":true,\\\"name\\\":\\\"_owner\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":true,\\\"name\\\":\\\"_spender\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"Unapproved\\\",\\\"type\\\":\\\"event\\\"}]";
        String s = NumericUtil.cleanHexPrefix(data);
        LogUtils.e("abi",s);
    }

    @Override
    public void configViews() {
        tvIdentityName.setText(Identity.getCurrentIdentity().getMetadata().getName());
    }

    @OnClick({R.id.tv_identity_name,R.id.wallet_manage,R.id.address_manage})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_identity_name:
                intent = new Intent(getActivity(), IdentityActivity.class);
                break;
            case R.id.wallet_manage:
                intent = new Intent(getActivity(), WalletListActivity.class);
                break;
            case R.id.address_manage:
                intent = new Intent(getActivity(), AddressListActivity.class);
                break;
        }
        mContext.startActivity(intent);
    }
}
