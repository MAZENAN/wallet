package cn.part.wallet.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.consenlabs.tokencore.wallet.address.BitcoinAddressCreator;
import org.consenlabs.tokencore.wallet.model.BIP44Util;
import org.consenlabs.tokencore.wallet.model.ChainId;
import org.consenlabs.tokencore.wallet.model.ChainType;
import org.consenlabs.tokencore.wallet.transaction.BitcoinTransaction;
import org.consenlabs.tokencore.wallet.transaction.EthereumTransaction;
import org.consenlabs.tokencore.wallet.transaction.TxSignResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import cn.part.wallet.entity.Token;
import cn.part.wallet.service.ITestAPI;
import cn.part.wallet.service.repository.WalletRepository;
import cn.part.wallet.service.response.TestResult;
import cn.part.wallet.service.response.TradeResponse;
import cn.part.wallet.service.response.TxList;
import cn.part.wallet.service.response.btctest.Tx;
import cn.part.wallet.service.response.btctest.TxUnspent;
import cn.part.wallet.utils.Convert;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.MyThreadPool;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.utils.Util;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.part.wallet.service.response.Gasinfo.DataBean;

public class TransferViewModel extends AndroidViewModel {
    private MutableLiveData<DataBean> mGasData;
    private WalletRepository mWalletRepository;
    private MutableLiveData<TradeResponse> mEthTxResponse = new MutableLiveData<>();

    public TransferViewModel(@NonNull Application application) {
        super(application);
        mWalletRepository = WalletRepository.getInstance();
    }

    public MutableLiveData<DataBean> getGas(Token token) {
        if (mGasData==null) {
            mGasData = mWalletRepository.getGas(token);
        }
        return mGasData;
    }

    public MutableLiveData<TradeResponse> getEthTxResponse() {
        return mEthTxResponse;
    }

    public void createEthTrans(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data, String chainId, String pwd, Wallet ethereumWallet) {
            try {
                String txHash = getEthTxSign(nonce,gasPrice,gasLimit,to,value,data,chainId,pwd,ethereumWallet);
                LogUtils.i("Trans","生成trans:"+txHash);
                mWalletRepository.sendTrans(mEthTxResponse,ChainType.ETHEREUM,txHash);
            }catch (Exception e){
                ToastUtil.showToast("生成tx失败，密码错误" + e.getMessage());
                LogUtils.e("Trans","生成trans失败:"+ethereumWallet.getAddress()+ e.getMessage());
            }
    }

    private String getEthTxSign(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data, String chainId, String pwd, Wallet ethereumWallet) {

        LogUtils.e("trans","打包值:"+"nonce:"+nonce+" gasLimit:"+gasLimit+" to:"+to+" value:"+value);
        EthereumTransaction tran = new EthereumTransaction(nonce,gasPrice,gasLimit,to,value, data);
        TxSignResult result = tran.signTransaction("4", pwd, ethereumWallet);
        String signedTx = result.getSignedTx(); // This is the signature result which you need to broadcast.
        String txHash = result.getTxHash(); // This is txHash which you can use for locating your transaction record
        LogUtils.i("trans","tranSign:交易号广播"+signedTx);
        LogUtils.i("trans","txHash:交易查询号"+txHash);
        return signedTx;
    }

    public void createBtcTrans(String to, long amount,long fee, String pass, Wallet wallet) {
        LogUtils.i("trans","钱包地址"+wallet.getAddress());
        LogUtils.i("trans",wallet.getMetadata().getSource());
        LogUtils.i("trans",wallet.getMetadata().getMode());
        LogUtils.i("trans",wallet.getMetadata().getNetwork());
        LogUtils.i("trans",wallet.exportPrivateKey("123456"));


        MyThreadPool.execute(()->{
            try {
                String txHash = getBtcTxSign(to,amount,fee,pass,wallet);

                LogUtils.i("Trans","生成trans:"+txHash);

            }catch (Exception e){
//                ToastUtil.showToast("生成tx失败，密码错误" + e.getMessage());
                LogUtils.e("Trans","生成trans失败:"+e.getMessage());
            }
        });

    }

    private String getBtcTxSign(String to, long amount,long fee, String pass, Wallet wallet) {
        ArrayList<BitcoinTransaction.UTXO> utxo = new ArrayList<>();
        long currentAmount = 0L;//已累加的费用
        long changeAmount = 0L; //找零费用 = 累加费用 -（专账费用+矿工费）<0则余额不足 未消费列表为空余额不足



        List<Tx> txList = new ArrayList<>();
        Retrofit retrofit = Util.getRetrofit(ITestAPI.BTC_HOST);
        ITestAPI iTestAPI = retrofit.create(ITestAPI.class);
        Call<TestResult<TxUnspent>> call = iTestAPI.getTxUnspentList(ITestAPI.NET_TEST, wallet.getAddress());
        try {
            Response<TestResult<TxUnspent>> response = call.execute();
            LogUtils.e("http","响应码为："+response.code());
            if (response.code()==200) {
                txList.addAll(response.body().getData().getTxs());
            }else {
                LogUtils.e("http",response.code());
            }
        } catch (Exception e) {
            LogUtils.e("http",e.getMessage());
        }
        if (txList.size()==0) {
            ToastUtil.showToast("未消费列表为空,余额不足");
            return "";
        }
        for (Tx tx: txList) {
            utxo.add(new BitcoinTransaction.UTXO(
                    tx.getTxid(),
                    tx.getOutput_no(),
                    Convert.BTCToSatoshi(new BigDecimal(tx.getValue())),
                    to,
                    tx.getScript_hex(),
                    "0/0"
            ));
        }
        String signHex = "";
        LogUtils.e("Trans",utxo.size());
        LogUtils.e("Trans",utxo.get(0).getSequence());
        try{
            BitcoinTransaction tran = new BitcoinTransaction("momBAzrpTobud7KcsU3UcDVnpaV4U6ajrq",0,100000,3000,utxo);

            TxSignResult txSignResult = tran.signTransaction(Integer.toString(ChainId.BITCOIN_TESTNET), "123456", wallet);
            signHex = txSignResult.getTxHash();
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e("Trans",e.getMessage());
        }
LogUtils.i("Trans",signHex);
        return signHex;
    }
}
