package cn.part.wallet.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.xml.sax.ErrorHandler;

import java.util.List;

import cn.part.wallet.BuildConfig;
import cn.part.wallet.service.IEthHttp;
import cn.part.wallet.service.response.ETHGas;
import cn.part.wallet.service.response.ETHNonce;
import cn.part.wallet.service.response.EthGasPrice;
import cn.part.wallet.service.response.EthResponse;
import cn.part.wallet.service.response.EthTxInfo;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EthGasRepository {
    private IEthHttp iEthService;
    private static EthGasRepository ethGassRepository;

    private EthGasRepository() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
//                Timber.tag("OkHttp").d(message);
//                Logger.e("okhttp",message);
                LogUtils.e("trans","响应json"+message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//Level中还有其他等级
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ETHERSCAN_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        iEthService = retrofit.create(IEthHttp.class);
    }

    public static EthGasRepository getInstance() {
        if (ethGassRepository == null) {
            synchronized (EthGasRepository.class) {
                if (ethGassRepository==null) {
                    ethGassRepository = new EthGasRepository();
                }
            }
        }
        return ethGassRepository;
    }

    public MutableLiveData<EthResponse> postTrans (String hex){
        final MutableLiveData<EthResponse> data = new MutableLiveData<EthResponse>();

        iEthService.postTransfer("proxy","eth_sendRawTransaction",hex).enqueue(new Callback<EthResponse>(){
            @Override
            public void onResponse(Call<EthResponse> call, Response<EthResponse> response) {
                data.postValue(response.body());
                LogUtils.i("trans","发送成功"+response.body().getResult());
            }

            @Override
            public void onFailure(Call<EthResponse> call, Throwable t) {
                data.postValue(null);
                LogUtils.i("trans","请求失败："+ t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ETHNonce> getEthNonce(String address) {
        MutableLiveData<ETHNonce> data = new MutableLiveData<>();
        Call<ETHNonce> call = iEthService.getNonceByAddr("proxy", "eth_getTransactionCount", address, "latest");
        call.enqueue(new Callback<ETHNonce>() {
            @Override
            public void onResponse(Call<ETHNonce> call, Response<ETHNonce> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ETHNonce> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<EthTxInfo> getEthTx(String address,int type) {
        MutableLiveData<EthTxInfo> data = new MutableLiveData<>();
        Call<EthTxInfo> txCall = iEthService.getTxByAddr("account", "txlist", address,"desc");
        txCall.enqueue(new Callback<EthTxInfo>() {
            @Override
            public void onResponse(Call<EthTxInfo> call, Response<EthTxInfo> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<EthTxInfo> call, Throwable t) {
                if (type == TYPE_FIRST ||type==TYPE_REFRESH){
                    data.postValue(null);
                }
                ToastUtil.showToast("获取列表失败");
            }
        });
        return data;
    }

    public void reGetList(String address,MutableLiveData<EthTxInfo> data) {

        Call<EthTxInfo> txCall = iEthService.getTxByAddr("account", "txlist", address,"desc");
        txCall.enqueue(new Callback<EthTxInfo>() {
            @Override
            public void onResponse(Call<EthTxInfo> call, Response<EthTxInfo> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<EthTxInfo> call, Throwable t) {
                    data.postValue(null);
                ToastUtil.showToast("获取列表失败");
            }
        });
    }

    public static final int TYPE_FIRST = 0;
    public static final int TYPE_REFRESH = 1;
}
