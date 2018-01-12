package org.svishnyakov.bux.bot.service.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.math.BigDecimal;
import java.util.UUID;

@AutoValue
public abstract class DealClosed {

    @JsonCreator
    public static DealClosed create(@JsonProperty("positionId") UUID positionId,
                                    @JsonProperty("price") Price price,
                                    @JsonProperty("product") Product product,
                                    @JsonProperty("profitAndLoss") ProfitAndLoss profitAndLoss) {
        return new AutoValue_DealClosed(positionId, price, product, profitAndLoss);
    }

    public abstract UUID positionId();

    public abstract Price price();

    public abstract Product product();

    public abstract ProfitAndLoss profitAndLoss();

    @AutoValue
    public abstract static class ProfitAndLoss {
        @JsonCreator
        public static ProfitAndLoss create(@JsonProperty("currency") String currency,
                                           @JsonProperty("amount") BigDecimal amount) {
            return new AutoValue_DealClosed_ProfitAndLoss(currency, amount);
        }

        public abstract String currency();

        public abstract BigDecimal amount();
    }
}
