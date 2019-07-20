package cn.part.wallet.service;

import retrofit2.Call;
import cn.part.wallet.service.response.EthBalance;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITokenHttp {
    @GET("/api")
    Call<EthBalance> getBalance(@Query("module")String module,@Query("action")String action,@Query("address")String address,@Query("tag")String tag);
}
