package org.svishnyakov.bux.bot.service.trade;

import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

@AutoValue
public abstract class BuyOrder {

    public static Builder builder() {
        return new AutoValue_BuyOrder.Builder();
    }

    public static BuyOrder forProduct(String productId) {
        return builder()
                .direction("BUY")
                .investingAmount(InvestingAmount.create())
                .leverage(2)
                .productId(productId)
                .build();
    }

    public abstract String getProductId();

    public abstract InvestingAmount getInvestingAmount();

    public abstract Integer getLeverage();

    public abstract String getDirection();

    @AutoValue
    public abstract static class InvestingAmount {
        public static InvestingAmount create(String currency, int decimals, BigDecimal amount) {
            return new AutoValue_BuyOrder_InvestingAmount(currency, decimals, amount);
        }

        public static InvestingAmount create() {
            return new AutoValue_BuyOrder_InvestingAmount("BUX", 2, BigDecimal.valueOf(200));
        }

        public abstract String getCurrency();

        public abstract int getDecimals();

        public abstract BigDecimal getAmount();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder productId(String productId);

        public abstract Builder investingAmount(InvestingAmount investingAmount);

        public abstract Builder leverage(Integer leverage);

        public abstract Builder direction(String direction);

        public abstract BuyOrder build();
    }
}
