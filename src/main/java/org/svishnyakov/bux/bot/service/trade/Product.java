package org.svishnyakov.bux.bot.service.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Product {
    @JsonCreator
    public static Product create(@JsonProperty("displayName") String displayName,
                                 @JsonProperty("securityId") String securityId) {
        return new AutoValue_Product(displayName, securityId);
    }

    public abstract String displayName();

    public abstract String securityId();
}
