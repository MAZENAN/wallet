package cn.part.wallet.ui.activity;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import java.math.BigDecimal;
import java.math.BigInteger;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.entity.Token;
import cn.part.wallet.service.response.Gasinfo;
import cn.part.wallet.service.response.TradeResponse;
import cn.part.wallet.utils.Convert;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.view.PwdInputAlertDialog;
import cn.part.wallet.view.TransactionPreView;
import cn.part.wallet.viewmodel.TransferViewModel;

public class ETHTransferActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_btn)
    ImageView ivBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.et_transfer_address)
    EditText etTransferAddress;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.tv_gas_cost)
    TextView tvGasCost;
    @BindView(R.id.gas_price)
    TextView tvGasPrice;
    @BindView(R.id.lly_gas)
    LinearLayout llyGas;
    @BindView(R.id.lly_advance_param)
    LinearLayout llyAdvanceParam;
    @BindView(R.id.advanced_switch)
    Switch advancedSwitch;
    @BindView(R.id.custom_gas_price)
    EditText customGasPrice;
    @BindView(R.id.custom_gas_limit)
    EditText customGasLimit;

    private String walletAddr;
    private String contractAddress;
    private int decimals;
    private String symbol;
    private String netCost;




    private Boolean isCustomGas = false;
    private BigDecimal amountPrice;
    private BigInteger nonce = new BigInteger("-1"); //nonce 默认值
    private BigDecimal gasLow = new BigDecimal("1.00"); //gas最低值 默认值gwei
//    private BigDecimal gasFast = new BigDecimal("20");
    private BigDecimal gasFastest = new BigDecimal("20.00");//gas最高值默认值 gwei
    private BigInteger gasLimit = new BigInteger("21000");//gaslimit 默认值
    private BigDecimal gasPrice = gasLow;//gas price 默认值
    private BigDecimal currentGasPrice = gasLow;//单位gwei 当前gasprice

    private Dialog dialog;
    private BigDecimal tokenNum;
    private Wallet wallet;
    private TransferViewModel mTransferViewModel;
    private Token token;

    @Override
    protected void onBeforeSetContentLayout() { }

    @Override
    public int getLayoutId() {
        return R.layout.activity_eth_transfer;
    }

    @Override
    public void initToolBar() {
        ivBtn.setImageResource(R.drawable.ic_transfer_scanner);
        rlBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        token = intent.getParcelableExtra("token");
        walletAddr = token.getAddress();
        String walletId = intent.getStringExtra("wallet_id");
        contractAddress = token.getContractaddress();
        decimals = token.getTokenNum().toBigInteger().intValue();
        symbol = token.getTokenName();
        tokenNum = token.getTokenNum();
        wallet = WalletManager.mustFindWalletById(walletId);

        mTransferViewModel = ViewModelProviders.of(this).get(TransferViewModel.class);
        mTransferViewModel.getGas(token).observe(this,this::onGetGas);
        mTransferViewModel.getEthTxResponse().observe(this,this::onCreateTrade);

        tvTitle.setText(symbol + getString(R.string.transfer_title));
        amountPrice = Convert.calGas(currentGasPrice,new BigDecimal(gasLimit));
        LogUtils.i(TAG,"初始值amountPrice: "+amountPrice.toString());
    }

    /**
     * 提交交易后
     * @param tradeResponse
     */
    private void onCreateTrade(TradeResponse tradeResponse) {
        if (tradeResponse == null) {
            ToastUtil.showToast("交易失败");
        }else {
            if (tradeResponse.getStatus()==200){
                LogUtils.i(TAG,"返回交易hash"+tradeResponse.getData().getHash());
                String hash = tradeResponse.getData().getHash();
                //保存交易hash
            }else {
                ToastUtil.showToast(tradeResponse.getR());
            }
        }
    }

    /**
     * 获取到gas信息时的回调
     * @param dataBean
     */
    private void onGetGas(Gasinfo.DataBean dataBean) {
        if (dataBean != null) {
            LogUtils.i(TAG,"gas max"+dataBean.getMax());
            LogUtils.i(TAG,"gas min"+dataBean.getMin());
            LogUtils.i(TAG,"nonce"+dataBean.getMin());
            nonce = new BigInteger(dataBean.getNum().substring(2),16);
            gasLow = Convert.valueToGwei(dataBean.getMin());
            gasFastest = Convert.valueToGwei(dataBean.getMax());
            currentGasPrice = gasLow;
            updateView();
        }
    }

    @Override
    public void configViews() {
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    calculateGas(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        customGasPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        customGasLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tvTitle.setText(token.getTokenName()+"转账");
    }

    @OnCheckedChanged(R.id.advanced_switch)
    public void onCheckedChange(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId()==R.id.advanced_switch) {
            switchCustom(isChecked);
        }
    }

    /**
     * gas模式切换
     * @param isChecked
     */
    private void switchCustom(Boolean isChecked) {
        isCustomGas = isChecked;
        llyAdvanceParam.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        llyGas.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        LogUtils.i(TAG,isCustomGas);
    }

    @OnClick({R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                showTransInfoDialog();
                break;
            case R.id.confirm_button:
                PwdInputAlertDialog pwdDialog = new PwdInputAlertDialog(mContext);
                pwdDialog.show();
                pwdDialog.setOnOkClick(this::onTransConfirm);
                break;
        }
    }

    /**
     * 密码输入确认转账
     * @param pwd
     */
    private void onTransConfirm(String pwd) {
        mTransferViewModel.createEthTrans(
                nonce,
                Convert.gweiToWei(currentGasPrice),
                new BigInteger(gasLimit.toString()),
                etTransferAddress.getText().toString().trim(),
                Convert.etherToWei(etAmount.getText().toString().trim()),
                "",
                "4",
                pwd,
                wallet
        );
    }

    /**
     * 展示转账信息
     */
    private void showTransInfoDialog() {
        if (!checkValue()){
            return;
        }
        String toAddr = etTransferAddress.getText().toString().trim();
        String amount = etAmount.getText().toString().trim();
        netCost = amountPrice.toString()+ " eth";
        TransactionPreView confirmView = new TransactionPreView(this,this::onClick);
        confirmView.fillInfo(walletAddr, toAddr, " - " + amount + " " +  symbol, netCost, gasPrice.toBigInteger(), gasLimit);

        dialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        dialog.setContentView(confirmView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private boolean checkValue (){
        String toAddress = etTransferAddress.getText().toString().trim();
        String amount = etAmount.getText().toString().trim();

        if (toAddress.length()==0){
            ToastUtil.showToast("请输入转账地址");
            return false;
        }
        if (amount.length()==0){
            ToastUtil.showToast("请输入大于0的金额");
            return false;
        }
        BigDecimal toAmount = new BigDecimal(amount);
        if (toAmount.compareTo(BigDecimal.valueOf(0))!=1) { //TODO校验余额
            ToastUtil.showToast("请输入大于0的金额");
            return false;
        }
        return true;
    }

    /**
     * 根据当前用户拖拽进度计算并更新当前矿工费
     * @param progress
     */
    private void calculateGas(int progress) {
        BigDecimal diff = gasFastest.subtract(gasLow).setScale(2,BigDecimal.ROUND_CEILING);

        BigDecimal add = new BigDecimal(String.valueOf(progress))
                        .divide(new BigDecimal(String.valueOf(100)),2,BigDecimal.ROUND_CEILING)
                        .multiply(diff).setScale(2,BigDecimal.ROUND_CEILING);
        currentGasPrice = gasLow.add(add);
        updateView();
    }

    /**
     * 更新当前gas总价和矿工费的显示
     */
    private void updateView() {
        amountPrice = Convert.calGas(currentGasPrice,new BigDecimal(gasLimit));
        tvGasCost.setText(amountPrice.toString()+ " ether");
        tvGasPrice.setText(currentGasPrice.toString() + "gwei");
    }
}

