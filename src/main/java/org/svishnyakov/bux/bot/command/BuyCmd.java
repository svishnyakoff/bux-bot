package org.svishnyakov.bux.bot.command;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BuyCmd {
    public static BuyCmd create(String productId) {
        return new AutoValue_BuyCmd(productId);
    }

    public abstract String getProductId();
}
