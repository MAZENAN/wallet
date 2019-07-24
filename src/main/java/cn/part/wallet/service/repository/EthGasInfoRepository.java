package cn.part.wallet.service.repository;

import android.arch.lifecycle.MutableLiveData;

import cn.part.wallet.BuildConfig;
import cn.part.wallet.service.IEthHttp;
import cn.part.wallet.service.response.ETHGas;
import cn.part.wallet.service.response.EthGasPrice;
import cn.part.wallet.service.response.EthResponse;
import cn.part.wallet.utils.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EthGasInfoRepository {
    private IEthHttp iEthService;
    private static EthGasInfoRepository ethGassRepository;

    private EthGasInfoRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IEthHttp.ETH_GAS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iEthService = retrofit.create(IEthHttp.class);
    }

    public static EthGasInfoRepository getInstance() {
        if (ethGassRepository == null) {
            synchronized (EthGasInfoRepository.class) {
                if (ethGassRepository==null) {
                    ethGassRepository = new EthGasInfoRepository();
                }
            }
        }
        return ethGassRepository;
    }
    public MutableLiveData<ETHGas> getGas() {
        final MutableLiveData<ETHGas> data = new MutableLiveData<>();
        iEthService.getGas().enqueue(new Callback<ETHGas>() {
            @Override
            public void onResponse(Call<ETHGas> call, Response<ETHGas> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ETHGas> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }
}
