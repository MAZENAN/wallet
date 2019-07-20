package cn.part.wallet.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import org.consenlabs.tokencore.wallet.Identity;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;

import java.util.HashMap;
import java.util.List;

import cn.part.wallet.entity.WalletInfo;

import static android.content.Context.MODE_PRIVATE;

public class ProperyViewModel extends AndroidViewModel {
    private MutableLiveData<WalletInfo> wallet = null;

    public ProperyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData getLikedData() {
        WalletInfo walletInfo = null;
        if (wallet==null){
            wallet = new MutableLiveData<>();

            SharedPreferences read = getApplication().getSharedPreferences("ids", MODE_PRIVATE);
            if (null != read) {
                String ethId = read.getString("EthId", "");
                String btcId = read.getString("BtcId", "");
                Wallet ethWallet = WalletManager.mustFindWalletById(ethId);

                Wallet btcWallet = WalletManager.mustFindWalletById(btcId);
                String btcAddress = btcWallet.getAddress();
                String ethAddress = ethWallet.getAddress();
                walletInfo = new WalletInfo(ethWallet.getMetadata().getName(),"0x" + ethAddress,"123",ethWallet.getId());
                List<String> list = ethWallet.getMetadata().getBackup();
                for (String str:list){
                    Log.i("wallet_info",str);
                }

                wallet.setValue(walletInfo);
            }
        }
        return wallet;
    }


}
