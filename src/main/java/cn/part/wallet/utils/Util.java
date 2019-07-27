package cn.part.wallet.utils;

import org.consenlabs.tokencore.wallet.model.ChainType;

import java.math.BigDecimal;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Util {

    public static Retrofit getRetrofit(String url) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                LogUtils.e("http","响应json"+message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static String chainTypeDesc(String chainType) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(ChainType.ETHEREUM,"ETH");
        hashMap.put(ChainType.BITCOIN,"BTC");
        hashMap.put(ChainType.EOS,"EOS");
        return hashMap.get(chainType);
    }

    public static Boolean compareValue(String value1,String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return (b1.compareTo(b2)) > 0;
    }
}
