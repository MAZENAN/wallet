package cn.part.wallet.service;

import cn.part.wallet.service.response.TestResult;
import cn.part.wallet.service.response.btctest.TxUnspent;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ITestAPI {
    public static final String BTC_HOST = "https://chain.so";
    public static final String NET_TEST = "BTCTEST";
    public static final String NET_DOGE = "BTC";

    /**
     * 获得未花费的交易
     * @param net
     * @param address
     * @return
     */
    @GET("/api/v2/get_tx_unspent/{net}/{address}")
    Call<TestResult<TxUnspent>> getTxUnspentList(@Path("net")String net, @Path("address")String address);
}
