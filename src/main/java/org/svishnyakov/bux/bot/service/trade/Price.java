package org.svishnyakov.bux.bot.service.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

@AutoValue
public abstract class Price {
    @JsonCreator
    public static Price create(@JsonProperty("currency") String currency,
                               @JsonProperty("amount") BigDecimal amount) {
        return new AutoValue_Price(currency, amount);
    }

    public abstract String currency();

    public abstract BigDecimal amount();
}
