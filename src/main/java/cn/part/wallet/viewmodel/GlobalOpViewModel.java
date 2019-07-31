package cn.part.wallet.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.consenlabs.tokencore.foundation.utils.MnemonicUtil;
import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.consenlabs.tokencore.wallet.keystore.EOSKeystore;
import org.consenlabs.tokencore.wallet.keystore.HDMnemonicKeystore;
import org.consenlabs.tokencore.wallet.keystore.IMTKeystore;
import org.consenlabs.tokencore.wallet.keystore.V3MnemonicKeystore;
import org.consenlabs.tokencore.wallet.model.BIP44Util;
import org.consenlabs.tokencore.wallet.model.ChainType;
import org.consenlabs.tokencore.wallet.model.Metadata;
import org.consenlabs.tokencore.wallet.model.MnemonicAndPath;
import org.consenlabs.tokencore.wallet.model.Network;

import java.util.List;

import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.MyThreadPool;

public class GlobalOpViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> mLoadingData = new MutableLiveData<>();
    private MutableLiveData<String> mMnemonicData;
    private MutableLiveData<Boolean> mDeleteResultData;

    public GlobalOpViewModel(@NonNull Application application) {
        super(application);
        mLoadingData.setValue(false);
    }

    public MutableLiveData<Boolean> getLoadingData() {
        return mLoadingData;
    }

    public MutableLiveData<String> getMnemonicData() {
        if (mMnemonicData == null){
            mMnemonicData = new MutableLiveData<>();
        }
        return mMnemonicData;
    }

    public void setMnemonicData(String mnemonic) {
        getMnemonicData().postValue(mnemonic);
    }

    public void setLoadingData(Boolean bool) {
        mLoadingData.postValue(bool);
    }

    public MutableLiveData<Boolean> getDeleteResultData() {
        if (mDeleteResultData == null) {
           mDeleteResultData = new MutableLiveData<>();
        }
        return mDeleteResultData;
    }

    public void setDeleteResultData(Boolean bool) {
        getDeleteResultData().postValue(bool);
    }

    /**
     * 发起创建身份
     * @param identityName
     * @param pass
     * @param hint
     */
    public void postCreateIdentity(String identityName,String pass,String hint) {
        MyThreadPool.execute(()->{
            setLoadingData(true);
            createIdentity(identityName,pass,hint);
        });
    }


    /**
     * 发起删除身份
     * @param pass
     */
    public void postDeleteIdentity(String pass) {
        MyThreadPool.execute(()->{
            setLoadingData(true);
            deleteIdentity(pass);
        });
    }

    /**
     * 发起创建钱包
     * @param walletName
     * @param pass
     * @param hint
     */
    public void postCreateWallet(String type,String walletName,String pass,String hint) {
        MyThreadPool.execute(()->{
            setLoadingData(true);
            createWallet(type,walletName,pass,hint);
        });
    }

    /**
     * 删除当前身份
     * @param pass
     */
    private void deleteIdentity(String pass) {
        try {
            Identity identity = Identity.getCurrentIdentity();
            identity.deleteIdentity(pass);
            setLoadingData(false);
            setDeleteResultData(true);
        }catch (Exception e) {
            LogUtils.e("identity",e.getMessage());
            setDeleteResultData(false);
            setLoadingData(false);
        }
    }

    /**
     * 创建身份
     * @param identityName
     * @param pass
     * @param hint
     */
    private void createIdentity(String identityName,String pass,String hint) {
        try {
            Identity identity = Identity.createIdentity(identityName, pass, hint, Network.TESTNET, Metadata.FROM_NEW_IDENTITY);
            Wallet ethereumWallet = identity.getWallets().get(0);
            String ethereumId = ethereumWallet.getId();
            MnemonicAndPath ethereumMnemonic = WalletManager.exportMnemonic(ethereumId, pass);
//            SharedPreferences.Editor editor = getApplication().getApplicationContext().getSharedPreferences("default_wallet", MODE_PRIVATE).edit();
//            editor.putString("current_wallet_id", ethereumWallet.getId());
//            editor.commit();
            getMnemonicData().postValue(ethereumMnemonic.getMnemonic());
        }catch (Exception e){
            getMnemonicData().postValue("");
        }
        mLoadingData.postValue(false);
    }

    /**
     * 创建钱包
     * @param walletType 钱包类型
     * @param walletName
     * @param pass
     * @param hint
     */
    private void createWallet(String walletType,String walletName,String pass,String hint) {


        Identity identity = Identity.getCurrentIdentity();
        List<String> mnemonics = MnemonicUtil.randomMnemonicCodes();
        Metadata walletMetadata = new Metadata();
        walletMetadata.setPasswordHint(hint);
//        walletMetadata.setSource(Metadata.FROM_MNEMONIC); //TODO 不确定
        walletMetadata.setSource(Metadata.NONE);
        walletMetadata.setName(walletName);

        IMTKeystore keystore = null;
        switch (walletType){
            case ChainType.ETHEREUM:
                walletMetadata.setChainType(ChainType.ETHEREUM);
                String ETHEREUM_HD_PATH = "m/44'/60'/0'/0/0";
                keystore = V3MnemonicKeystore.create(walletMetadata, pass, mnemonics, ETHEREUM_HD_PATH);
                break;
            case ChainType.BITCOIN:
                walletMetadata.setChainType(ChainType.BITCOIN);
                walletMetadata.setSegWit(Metadata.P2WPKH);
                String path = BIP44Util.BITCOIN_SEGWIT_MAIN_PATH;
                keystore = HDMnemonicKeystore.create(walletMetadata, pass, mnemonics, path);

                //来自identity源码
//                Metadata walletMetadata = new Metadata();
//                walletMetadata.setChainType(ChainType.BITCOIN);
//                walletMetadata.setPasswordHint(this.getMetadata().getPasswordHint());
//                walletMetadata.setSource(this.getMetadata().getSource());
//                walletMetadata.setNetwork(this.getMetadata().getNetwork());
//                walletMetadata.setName("BTC");
//                walletMetadata.setSegWit(segWit);
//                String path;
//                if (Metadata.P2WPKH.equals(segWit)) {
//                    path =  this.getMetadata().isMainNet() ? BIP44Util.BITCOIN_SEGWIT_MAIN_PATH : BIP44Util.BITCOIN_SEGWIT_TESTNET_PATH;
//                } else {
//                    path = this.getMetadata().isMainNet() ? BIP44Util.BITCOIN_MAINNET_PATH : BIP44Util.BITCOIN_TESTNET_PATH;
//                }
//
//                IMTKeystore keystore = HDMnemonicKeystore.create(walletMetadata, password, mnemonicCodes, path);
//                return WalletManager.createWallet(keystore);

                break;
            case ChainType.EOS:
                walletMetadata.setChainType(ChainType.EOS);
                keystore = EOSKeystore.create(walletMetadata, pass, "", mnemonics, BIP44Util.EOS_LEDGER, null);
                break;
        }

        Wallet wallet = new Wallet(keystore);
        identity.addWallet(wallet);
    }
}
