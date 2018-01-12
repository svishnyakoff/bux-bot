package org.svishnyakov.bux.bot;

import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

@AutoValue
public abstract class BotArgs {
    public static BotArgs create(String productId, BigDecimal buyPrice, BigDecimal lowerSellPrice, BigDecimal upperSellPrice) {
        return new AutoValue_BotArgs(productId, buyPrice, lowerSellPrice, upperSellPrice);
    }

    public abstract String productId();

    public abstract BigDecimal buyPrice();

    public abstract BigDecimal lowerSellPrice();

    public abstract BigDecimal upperSellPrice();
}
