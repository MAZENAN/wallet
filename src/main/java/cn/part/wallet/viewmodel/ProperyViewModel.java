package cn.part.wallet.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import java.util.List;
import cn.part.wallet.entity.WalletInfo;
import cn.part.wallet.utils.MyThreadPool;
import static android.content.Context.MODE_PRIVATE;

public class ProperyViewModel extends AndroidViewModel {
    private MutableLiveData<WalletInfo> wallet = null;

    private MutableLiveData<List<Wallet>> walletList;
    private MutableLiveData<Wallet> currentWallet;

    public ProperyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData getLikedData() {
        WalletInfo walletInfo = null;
        if (wallet==null){
            wallet = new MutableLiveData<>();

            SharedPreferences read = getApplication().getSharedPreferences("default_wallet", MODE_PRIVATE);
            if (null != read) {
                String wallet_id = read.getString("current_wallet_id", "");
                Wallet cur_wallet = Identity.getCurrentIdentity().getWallets().get(0);
//                Wallet cur_wallet = WalletManager.mustFindWalletById(wallet_id);

                String ethAddress = cur_wallet.getAddress();
                walletInfo = new WalletInfo(cur_wallet.getMetadata().getName(),"0x" + ethAddress,"123",cur_wallet.getId());
                List<String> list = cur_wallet.getMetadata().getBackup();
                this.wallet.setValue(walletInfo);
            }
        }
        return wallet;
    }

    public MutableLiveData<Wallet> getDefaultWallet() {
        if (currentWallet==null) {
            currentWallet = new MutableLiveData<>();
            MyThreadPool.execute(this::loadDefaultWallet);
        }
        return currentWallet;
    }

    public void setDefaultWallet(Wallet wallet) {
        getDefaultWallet().postValue(wallet);
    }

    private void loadDefaultWallet() {
        getDefaultWallet();
        Wallet wallet = Identity.getCurrentIdentity().getWallets().get(0);
        currentWallet.postValue(wallet);
    }


    public MutableLiveData<List<Wallet>> getWalletList() {
        if (walletList== null) {
            walletList = new MutableLiveData<>();
            MyThreadPool.execute(this::getAllWallets);
        }
        return walletList;
    }

    private void getAllWallets() {
        List<Wallet> wallets = Identity.getCurrentIdentity().getWallets();
        walletList.postValue(wallets);
    }

    public void refreshWallets() {
        getWalletList();
        MyThreadPool.execute(this::getAllWallets);
    }
}
