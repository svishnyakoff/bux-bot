package org.svishnyakov.bux.bot.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.svishnyakov.bux.bot.ConsoleLogger;
import org.svishnyakov.bux.bot.command.BuyCmd;
import org.svishnyakov.bux.bot.event.TradingQuoteEvent;

import java.math.BigDecimal;

/**
 * Monitors the prices and signals parent when it's time to buy.
 */
public class PriceWatchdogActor extends AbstractActor {

    private final String product;
    private final BigDecimal expectedPrice;
    private final BigDecimal lowerPrice;

    public PriceWatchdogActor(String product, BigDecimal expectedPrice, BigDecimal lowerPrice) {
        this.product = product;
        this.expectedPrice = expectedPrice;
        this.lowerPrice = lowerPrice;
    }

    public static Props props(String product, BigDecimal expectedPrice, BigDecimal lowerPrice) {
        return Props.create(PriceWatchdogActor.class, product, expectedPrice, lowerPrice);
    }

    private static boolean lessOrEqual(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) <= 0;
    }

    private static boolean greaterThan(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) > 0;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TradingQuoteEvent.class, this::onPriceChangeEvent)
                .build();
    }

    private void onPriceChangeEvent(TradingQuoteEvent event) {
        BigDecimal currentPrice = event.currentPrice();

        ConsoleLogger.log("Product[{}] has price {}. Waiting for price {} or less", product, currentPrice,
                          expectedPrice);

        if (lessOrEqual(currentPrice, expectedPrice) && greaterThan(currentPrice, lowerPrice)) {
            getContext().getParent().tell(BuyCmd.create(product), getSelf());
            getContext().stop(getSelf());
        }
    }
}
