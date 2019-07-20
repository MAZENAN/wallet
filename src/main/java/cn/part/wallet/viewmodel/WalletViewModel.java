package cn.part.wallet.viewmodel;

import android.app.Application;
import android.app.WallpaperInfo;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;

import java.util.ArrayList;
import java.util.List;

import cn.part.wallet.entity.WalletInfo;

public class WalletViewModel extends AndroidViewModel {
    private MutableLiveData<List<WalletInfo>> walletsLiveData;
    private MutableLiveData<Wallet> currentWallet;

    public WalletViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<WalletInfo>> getWalletListLiveData() {
        if (walletsLiveData==null){
            walletsLiveData = new MutableLiveData<>();
            List<Wallet> wallets = Identity.getCurrentIdentity().getWallets();
            List<WalletInfo> walletInfoList = new ArrayList<>();
            for (Wallet wallet:wallets){
                WalletInfo walletInfo = new WalletInfo(wallet.getMetadata().getName(),wallet.getAddress(),"",wallet.getId());
                walletInfoList.add(walletInfo);
            }
            walletsLiveData.setValue(walletInfoList);
        }
        return walletsLiveData;
    }
}
