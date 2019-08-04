package cn.part.wallet.service.repository;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import cn.part.wallet.R;
import cn.part.wallet.entity.Token;
import cn.part.wallet.entity.TxInfo;
import cn.part.wallet.service.IWalletApi;
import cn.part.wallet.service.response.Gasinfo;
import cn.part.wallet.service.response.TradeResponse;
import cn.part.wallet.service.response.TxList;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.utils.Util;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WalletRepository {
    private IWalletApi iWalletService;
    private static WalletRepository mWalletRepository;

    private WalletRepository() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                LogUtils.e("http","响应json"+message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//Level中还有其他等级
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IWalletApi.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        iWalletService = retrofit.create(IWalletApi.class);
    }

    public static WalletRepository getInstance() {
        if (mWalletRepository == null) {
            synchronized (WalletRepository.class) {
                if (mWalletRepository ==null) {
                    mWalletRepository = new WalletRepository();
                }
            }
        }
        return mWalletRepository;
    }

    public MutableLiveData<List<TxInfo>> getTxListData(Token token) {
        MutableLiveData<List<TxInfo>> data = new MutableLiveData<>();
        callGetTxlist(data,token);
        return data;
    }

    public void reGetTxList(MutableLiveData<List<TxInfo>> data,Token token) {
        callGetTxlist(data,token);
    }

    private void callGetTxlist(MutableLiveData<List<TxInfo>> data,Token token) {
        Call<TxList> txCall = iWalletService.getHistory(Util.chainTypeDesc(token.getType()),token.getAddress(),Util.getNetEnv());
        txCall.enqueue(new Callback<TxList>() {
            @Override
            public void onResponse(Call<TxList> call, Response<TxList> response) {
                if (response.body().getStatus()==200){
                    data.postValue(response.body().getData().getHistory_list());
                } else {
                    data.postValue(null);
                    ToastUtil.showToast(R.string.get_txlist_fail);
                }
            }

            @Override
            public void onFailure(Call<TxList> call, Throwable t) {
                data.postValue(null);
                LogUtils.e("http",t.getMessage());
                ToastUtil.showToast(R.string.get_txlist_fail);
            }
        });
    }

    public MutableLiveData<Gasinfo.DataBean> getGas(Token token) {
        final MutableLiveData<Gasinfo.DataBean> data = new MutableLiveData<>();
        iWalletService.getFee(Util.chainTypeDesc(token.getType()),token.getAddress(),Util.getNetEnv()).enqueue(new Callback<Gasinfo>() {
            @Override
            public void onResponse(Call<Gasinfo> call, Response<Gasinfo> response) {
                if (response.body().getStatus()==200) {
                    data.postValue(response.body().getData());
                }else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Gasinfo> call, Throwable t) {
                ToastUtil.showToast("网络连接失败");
                data.postValue(null);
            }
        });
        return data;
    }

    public void sendTrans (MutableLiveData<TradeResponse> data,String type,String hex){
            iWalletService.sendTrans(Util.chainTypeDesc(type),hex,Util.getNetEnv()).enqueue(new Callback<TradeResponse>() {
                @Override
                public void onResponse(Call<TradeResponse> call, Response<TradeResponse> response) {
                    data.postValue(response.body());
                    LogUtils.i("http","发送成功"+response.body());
                }

                @Override
                public void onFailure(Call<TradeResponse> call, Throwable t) {
                    data.postValue(null);
                    LogUtils.e("http","请求失败："+ t.getMessage());
                }
            });
    }
}
