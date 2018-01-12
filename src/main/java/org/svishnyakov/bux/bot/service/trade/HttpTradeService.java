package org.svishnyakov.bux.bot.service.trade;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.svishnyakov.bux.bot.ConnectionConfig;
import org.svishnyakov.bux.bot.Mapper;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.UUID;

public class HttpTradeService implements TradeService {

    private static final Retrofit RETROFIT_CLIENT = setupRetrofitClient();

    private final RetrofitTradeService retrofitTradeService = RETROFIT_CLIENT.create(RetrofitTradeService.class);

    private static Retrofit setupRetrofitClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("Authorization",
                                                                     ConnectionConfig.getSecuredHeader()).build();
            return chain.proceed(request);
        });

        return new Retrofit.Builder()
                .baseUrl(ConnectionConfig.getApiUrl())
                .addConverterFactory(JacksonConverterFactory.create(Mapper.get()))
                .client(httpClient.build())
                .build();
    }

    @Override
    public DealOpened buyProduct(String productId) {
        try {
            Call<DealOpened> dealOpenedCall = retrofitTradeService.buy(BuyOrder.forProduct(productId));
            return dealOpenedCall.execute().body();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DealClosed sell(UUID positionId) {
        try {
            return retrofitTradeService.sell(positionId).execute().body();
        }
        catch (IOException e) {
            throw new TradeException(e);
        }
    }
}
