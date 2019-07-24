package cn.part.wallet.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.transaction.EthereumTransaction;
import org.consenlabs.tokencore.wallet.transaction.TxSignResult;

import java.math.BigInteger;

import cn.part.wallet.service.repository.EthGasInfoRepository;
import cn.part.wallet.service.repository.EthGasRepository;
import cn.part.wallet.service.response.ETHGas;
import cn.part.wallet.service.response.ETHNonce;
import cn.part.wallet.service.response.EthGasPrice;
import cn.part.wallet.service.response.EthResponse;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;

public class EthViewmodel extends AndroidViewModel {
    private EthGasRepository ethGasRepository;
    private EthGasInfoRepository ethGasInfoRepository;
    private MutableLiveData<ETHGas> ethGasData;
    private MutableLiveData<EthResponse> ethTxResponse;
    private MutableLiveData<ETHNonce> ethNonce;
    public EthViewmodel(@NonNull Application application) {
        super(application);
        ethGasRepository = EthGasRepository.getInstance();
        ethGasInfoRepository = EthGasInfoRepository.getInstance();
    }

    public MutableLiveData<ETHGas> getETHGas() {
        if (ethGasData==null) {
            ethGasData = ethGasInfoRepository.getGas();
        }
        return ethGasData;
    }

    public void setGasData(ETHGas gas) {
        ethGasData.setValue(gas);
    }

    public MutableLiveData<EthResponse> createTrans(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data, String chainId, String pwd, Wallet ethereumWallet) {
        if (ethTxResponse==null) {
            try {
                String txHash = getSignTx(nonce,gasPrice,gasLimit,to,value,data,chainId,pwd,ethereumWallet);
                LogUtils.i("Trans","生成trans:"+txHash);
                ethTxResponse= ethGasRepository.postTrans(txHash);
            }catch (Exception e){
                ethTxResponse = null;
                ToastUtil.showToast("生成tx失败，密码错误" + e.getMessage());
                LogUtils.e("Trans","生成trans失败:"+ethereumWallet.getAddress()+ e.getMessage());
            }
        }
        return ethTxResponse;
    }

    private String getSignTx(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data, String chainId, String pwd, Wallet ethereumWallet) {

        LogUtils.e("trans","打包值:"+"nonce:"+nonce+" gasLimit:"+gasLimit+" to:"+to+" value:"+value);
            EthereumTransaction tran = new EthereumTransaction(nonce,gasPrice,gasLimit,to,value, data);
        TxSignResult result = tran.signTransaction("4", pwd, ethereumWallet);
        String signedTx = result.getSignedTx(); // This is the signature result which you need to broadcast.
        String txHash = result.getTxHash(); // This is txHash which you can use for locating your transaction record
        LogUtils.i("trans","tranSign:交易号广播"+signedTx);
        LogUtils.i("trans","txHash:交易查询号"+txHash);
        return signedTx;
    }

    public MutableLiveData<ETHNonce> getEthNonce(String address) {
        if (ethNonce==null){
            ethNonce = ethGasRepository.getEthNonce(address);
        }
        return ethNonce;
    }
}
