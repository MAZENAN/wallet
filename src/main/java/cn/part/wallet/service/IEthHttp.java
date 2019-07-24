package cn.part.wallet.service;

import android.arch.lifecycle.MutableLiveData;

import cn.part.wallet.service.response.ETHGas;
import cn.part.wallet.service.response.ETHNonce;
import cn.part.wallet.service.response.EthBlockNum;
import cn.part.wallet.service.response.EthGasPrice;
import cn.part.wallet.service.response.EthResponse;
import cn.part.wallet.service.response.EthTxInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IEthHttp {
    public static final String ETH_HOST = "https://api.etherscan.io";
    public static final String TEST_ETH_HOST = "https://api.etherscan.io";
    public static final String ETH_GAS = "https://ethgasstation.info";
    //获取区块号
    @GET("/api") //?module=proxy&action=eth_blockNumber&apikey=YourApiKeyToken
    Call<EthBlockNum> getBlockNum(@Query("module") String module, @Query("action") String action);

    //提交交易
    @FormUrlEncoded
    @POST("/api") //?module=proxy&action=eth_blockNumber&apikey=YourApiKeyToken
    Call<EthResponse> postTransfer(@Query("module") String module, @Query("action") String action, @Field("hex") String hex);

//    https://ethgasstation.info/json/ethgasAPI.json
    @GET("/json/ethgasAPI.json")
    Call<ETHGas> getGas();

    @GET("/api")//module=proxy&action=eth_getTransactionCount&address=0xf925cbff181eb7c82d5b34401e512d3781461863&tag=latest&apikey=YourApiKeyToken
    Call<ETHNonce> getNonceByAddr(@Query("module") String module,@Query("action") String action,@Query("address") String address,@Query("tag") String tag);

    @GET("/api")///api?module=account&action=txlist&address=0xf925cbff181eb7c82d5b34401e512d3781461863
    Call<EthTxInfo>  getTxByAddr(@Query("module") String module,@Query("action") String action,@Query("address") String address,@Query("sort") String sort);
}
