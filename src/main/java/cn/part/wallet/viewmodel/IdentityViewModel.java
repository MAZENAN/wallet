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
import org.consenlabs.tokencore.wallet.model.Metadata;

import java.util.HashMap;
import java.util.List;

import cn.part.wallet.entity.IdentityInfo;
import cn.part.wallet.entity.WalletInfo;

import static android.content.Context.MODE_PRIVATE;

public class IdentityViewModel extends AndroidViewModel {
    private MutableLiveData<IdentityInfo> identity = null;

    public IdentityViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData getLikedData() {
        IdentityInfo identityInfo = null;
        if (identity==null){
            identity = new MutableLiveData<>();
            Identity currentIdentity = Identity.getCurrentIdentity();
            Metadata metadata = currentIdentity.getMetadata();
            String name = metadata.getName();
            String ID = currentIdentity.getIdentifier();
            identityInfo = new IdentityInfo(name,ID);
            identity.setValue(identityInfo);
        }
        return identity;
    }


}
