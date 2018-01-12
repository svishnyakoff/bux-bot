package org.svishnyakov.bux.bot.command;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class SubscribeCmd {

    private static final String SUBSCRIBE_TEMPLATE = "trading.product.%s";

    public static SubscribeCmd create(String productId) {
        return new AutoValue_SubscribeCmd(ImmutableList.of(String.format(SUBSCRIBE_TEMPLATE, productId)));
    }

    public abstract ImmutableList<String> getSubscribeTo();
}
