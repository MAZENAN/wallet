package cn.part.wallet.ui.activity;

import android.widget.TextView;

import butterknife.BindView;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.entity.TransInfo;
import cn.part.wallet.service.response.EthTxInfo;
import cn.part.wallet.utils.Convert;

public class TxDetailActivity extends BaseActivity {
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_to)
    TextView tvTo;
    @BindView(R.id.tv_gas_fee)
    TextView tvGasFee;
    @BindView(R.id.tv_txn_hash)
    TextView tvTxnHash;
    @BindView(R.id.tv_txn_time)
    TextView tvTxnTime;
    @BindView(R.id.tv_block_number)
    TextView tvBlockNumber;

    private TransInfo txDetail;
    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_detail;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        txDetail = getIntent().getParcelableExtra("transInfo");
    }

    @Override
    public void configViews() {
        tvAmount.setText(Convert.weiToEther(txDetail.getValue()).toString()+ " eth");
        tvFrom.setText(txDetail.getFrom());
        tvTo.setText(txDetail.getTo());
        tvBlockNumber.setText(txDetail.getBlockNumber());
        tvGasFee.setText(txDetail.getGasPrice());
        tvTxnHash.setText(txDetail.getHash());

        tvTxnTime.setText(Convert.formateDate(txDetail.getTimeStamp()));
    }

}
