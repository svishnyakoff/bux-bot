package org.svishnyakov.bux.bot.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.math.BigDecimal;


@AutoValue
public abstract class TradingQuoteEvent implements Event {

    @JsonCreator
    public static TradingQuoteEvent create(@JsonProperty("securityId") String securityId,
                                           @JsonProperty("currentPrice") BigDecimal currentPrice) {
        return new AutoValue_TradingQuoteEvent(securityId, currentPrice);
    }

    public abstract String securityId();
    public abstract BigDecimal currentPrice();
}
