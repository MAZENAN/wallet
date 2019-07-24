package cn.part.wallet.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import cn.part.wallet.service.repository.EthGasRepository;
import cn.part.wallet.service.response.EthTxInfo;
import cn.part.wallet.utils.LogUtils;

public class PropertyDetailViewModel extends AndroidViewModel {
    private MutableLiveData<EthTxInfo> ethTxList;
    private MutableLiveData<Boolean> loadind = new MutableLiveData<>();

    private EthGasRepository ethGasRepository;
    public PropertyDetailViewModel(@NonNull Application application) {
        super(application);
        ethGasRepository = EthGasRepository.getInstance();
        loadind.setValue(false);
    }

    public MutableLiveData<EthTxInfo> getEthTxList(String address) {
        if (ethTxList==null) {
            setLoading(true);
            ethTxList = ethGasRepository.getEthTx(address,EthGasRepository.TYPE_FIRST);
        }
        return ethTxList;
    }

    public void refreshTxList(String address) {
        LogUtils.i("txlist","获取列表");
        ethGasRepository.reGetList(address,ethTxList);
    }

    public MutableLiveData<Boolean> getLoadind() {
        return loadind;
    }

    public void setLoading(Boolean isLoading) {
        loadind.setValue(isLoading);
    }
}
