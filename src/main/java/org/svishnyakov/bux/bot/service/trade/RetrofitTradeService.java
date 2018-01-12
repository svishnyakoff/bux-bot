package org.svishnyakov.bux.bot.service.trade;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

/**
 * Retrofit interface for calling API.
 */
public interface RetrofitTradeService {

    @POST("core/16/users/me/trades")
    Call<DealOpened> buy(@Body BuyOrder buyOrder);

    @DELETE("core/16/users/me/portfolio/positions/{getPositionId}")
    Call<DealClosed> sell(@Path("getPositionId") UUID positionId);
}
