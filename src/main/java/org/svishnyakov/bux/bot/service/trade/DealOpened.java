package org.svishnyakov.bux.bot.service.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.util.UUID;

@AutoValue
public abstract class DealOpened {

    @JsonCreator
    public static DealOpened create(@JsonProperty("positionId") UUID positionId,
                                    @JsonProperty("price") Price price,
                                    @JsonProperty("product") Product product) {
        return new AutoValue_DealOpened(positionId, price, product);
    }

    public abstract UUID positionId();

    public abstract Price price();

    public abstract Product product();
}
