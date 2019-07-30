package cn.part.wallet.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.consenlabs.tokencore.wallet.model.Metadata;
import org.consenlabs.tokencore.wallet.model.MnemonicAndPath;
import org.consenlabs.tokencore.wallet.model.Network;
import cn.part.wallet.utils.MyThreadPool;
import cn.part.wallet.utils.ToastUtil;

public class GlobalOpViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> mLoadingData = new MutableLiveData<>();
    private MutableLiveData<String> mMnemonicData = new MutableLiveData<>();

    public GlobalOpViewModel(@NonNull Application application) {
        super(application);
        mLoadingData.setValue(false);
    }

    public MutableLiveData<Boolean> getLoadingData() {
        return mLoadingData;
    }

    public MutableLiveData<String> getMnemonicData() {
        return mMnemonicData;
    }

    public void setMnemonicData(String mnemonic) {
        mMnemonicData.postValue(mnemonic);
    }

    public void setLoadingData(Boolean bool) {
        mLoadingData.postValue(bool);
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

    private void createIdentity(String identityName,String pass,String hint) {
        try {
            Identity identity = Identity.createIdentity(identityName, pass, hint, Network.MAINNET, Metadata.P2WPKH);
            Wallet ethereumWallet = identity.getWallets().get(0);
            String ethereumId = ethereumWallet.getId();
            MnemonicAndPath ethereumMnemonic = WalletManager.exportMnemonic(ethereumId, pass);
//            SharedPreferences.Editor editor = getApplication().getApplicationContext().getSharedPreferences("default_wallet", MODE_PRIVATE).edit();
//            editor.putString("current_wallet_id", ethereumWallet.getId());
//            editor.commit();
            mMnemonicData.postValue(ethereumMnemonic.getMnemonic());
        }catch (Exception e){
            mMnemonicData.postValue("");
        }
        mLoadingData.postValue(false);
    }
}
