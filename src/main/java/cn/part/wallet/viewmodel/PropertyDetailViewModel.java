package cn.part.wallet.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import java.util.List;
import cn.part.wallet.entity.Token;
import cn.part.wallet.entity.TxInfo;
import cn.part.wallet.service.repository.WalletRepository;

public class PropertyDetailViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> loadind = new MutableLiveData<>();
    private MutableLiveData<List<TxInfo>> mTxlistData;
    private WalletRepository mWalletRepository;

    public PropertyDetailViewModel(@NonNull Application application) {
        super(application);
        mWalletRepository = WalletRepository.getInstance();
        loadind.setValue(false);
    }

    public MutableLiveData<List<TxInfo>> getETxList(Token token) {
        if (mTxlistData==null) {
            setLoading(true);
            mTxlistData = mWalletRepository.getTxListData(token);
        }
        return mTxlistData;
    }

    public void refreshTxList(Token token) {
        mWalletRepository.reGetTxList(mTxlistData,token);
    }

    public MutableLiveData<Boolean> getLoadind() {
        return loadind;
    }

    public void setLoading(Boolean isLoading) {
        loadind.setValue(isLoading);
    }
}
