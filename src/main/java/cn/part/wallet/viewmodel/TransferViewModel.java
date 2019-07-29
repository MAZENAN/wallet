package cn.part.wallet.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.model.ChainId;
import org.consenlabs.tokencore.wallet.model.ChainType;
import org.consenlabs.tokencore.wallet.transaction.BitcoinTransaction;
import org.consenlabs.tokencore.wallet.transaction.EthereumTransaction;
import org.consenlabs.tokencore.wallet.transaction.TxSignResult;

import java.math.BigInteger;
import java.util.ArrayList;

import cn.part.wallet.entity.Token;
import cn.part.wallet.service.repository.WalletRepository;
import cn.part.wallet.service.response.TradeResponse;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;
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

//    private String getBtcTxSign(String to, long amount,long fee, String pass, Wallet wallet) {
//        ArrayList<BitcoinTransaction.UTXO> utxo = new ArrayList<>();
//        utxo.add(
//                new BitcoinTransaction.UTXO(
//                "e112b1215813c8888b31a80d215169809f7901359c0f4bf7e7374174ab2a64f4",
//                   0,
//                65000000,
//                        to,
//        "76a914899305a3f569188193fb75843b53c2b56b37988288ac",
//        null));
//        utxo.add()
//        BitcoinTransaction tran = new BitcoinTransaction(to,1,amount,fee,utxo);
//        TxSignResult txSignResult = tran.signTransaction(Integer.toString(ChainId.BITCOIN_TESTNET), pass, wallet);
//        return txSignResult.getTxHash();
//    }
}
