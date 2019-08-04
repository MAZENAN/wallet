package cn.part.wallet.service;

import cn.part.wallet.service.response.Gasinfo;
import cn.part.wallet.service.response.TokenBalance;
import cn.part.wallet.service.response.TradeResponse;
import cn.part.wallet.service.response.TxList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IWalletApi {
    public static final String HOST = "http://wallet.part.cn";
    public static final String NET_DOGE = "DOGE";
    public static final String NET_TEST = "test";

    //获取余额
    // /app_api/v1/get_balance.php?type=ETH&address=0xf925CBFF181Eb7c82d5B34401e512d3781461863&network=test
    @GET("/app_api/v1/get_balance.php")
    Call<TokenBalance> getBalance(@Query("type") String type, @Query("address") String address, @Query("network") String network);

    //获取代币余额
    @GET("/app_api/v1/get_balance.php")
    Call<TokenBalance> getTokenBalance(@Query("type") String type, @Query("address") String address, @Query("token") String token);

    //获取gas获取nonce
    // http://test.part.cn/app_api/v1/get_fee.php?type=ETH&address=0xf925CBFF181Eb7c82d5B34401e512d3781461863
    @GET("/app_api/v1/get_fee.php")
    Call<Gasinfo> getFee(@Query("type") String type, @Query("address") String address,@Query("network") String network);

    //获取交易记录
    //http://test.part.cn/app_api/v1/history.php?type=BTC&address=DFundmtrigzA6E25Swr2pRe4Eb79bGP8G1&network=DOGE
    //http://test.part.cn/app_api/v1/history.php?type=ETH&address=0xf925CBFF181Eb7c82d5B34401e512d3781461863&network=test
    @GET("/app_api/v1/history.php")
    Call<TxList> getHistory(@Query("type") String type, @Query("address") String address, @Query("network") String network);

    //保存交易hash
    // http://test.part.cn/app_api/v1/save_data.php?type=BTC&address=DFundmtrigzA6E25Swr2pRe4Eb79bGP8G1&network=DOGE
    // http://test.part.cn/app_api/v1/save_data.php?type=ETH&address=0xf925CBFF181Eb7c82d5B34401e512d3781461863&network=test
    @FormUrlEncoded
    @POST("/app_api/v1/save_data.php")
    Call saveTransHash(@Query("type") String type,@Query("address") String address,@Query("network") String network,@Field("wallet_address") String wallet_address,@Field("exchange_hash") String exchange_hash);

    //将交易hex发送到网络节点
    /*
http://www.test.com/app_api/v1/trade.php?type=ETH&hex=0x6c0c2048e5928d459d90076fecffe96105e01a9fc43fa7fcc02501a00103064a&network=test
http://www.test.com/app_api/v1/trade.php?type=BTC&hex=0x6c0c2048e5928d459d90076fecffe96105e01a9fc43fa7fcc02501a00103064a&network=test
 */
    @GET("/app_api/v1/trade.php")
    Call<TradeResponse> sendTrans(@Query("type") String type, @Query("hex") String hex, @Query("network") String network);
}
