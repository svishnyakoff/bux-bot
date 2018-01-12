package org.svishnyakov.bux.bot.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.svishnyakov.bux.bot.ConsoleLogger;
import org.svishnyakov.bux.bot.command.SellCmd;
import org.svishnyakov.bux.bot.event.TradingQuoteEvent;
import org.svishnyakov.bux.bot.service.trade.DealOpened;

import java.math.BigDecimal;

/**
 * Monitors prices and signals parent when it's a time to sell.
 */
public class DealCloserActor extends AbstractActor {

    private final String productId;
    private final DealOpened dealOpenedInfo;
    private final BigDecimal lowerPrice;
    private final BigDecimal upperPrice;

    public DealCloserActor(String productId, DealOpened dealOpenedInfo, BigDecimal lowerPrice, BigDecimal upperPrice) {
        this.productId = productId;
        this.dealOpenedInfo = dealOpenedInfo;
        this.lowerPrice = lowerPrice;
        this.upperPrice = upperPrice;
    }

    public static Props props(String productId, DealOpened dealOpened, BigDecimal lowerPrice, BigDecimal upperPrice) {
        return Props.create(DealCloserActor.class, productId, dealOpened, lowerPrice, upperPrice);
    }

    private static boolean lessOrEqual(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) <= 0;
    }

    private static boolean greaterOrEqual(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) >= 0;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TradingQuoteEvent.class, this::onPriceChanged)
                .build();
    }

    private void onPriceChanged(TradingQuoteEvent priceChangeEvent) {
        if (!productId.equals(priceChangeEvent.securityId())) {
            return;
        }

        BigDecimal currentPrice = priceChangeEvent.currentPrice();

        ConsoleLogger.log("Product[{}] has price {}. Trading while in range [{}; {}]",
                          dealOpenedInfo.product().securityId(), currentPrice, lowerPrice, upperPrice);


        if (lessOrEqual(currentPrice, lowerPrice) || greaterOrEqual(currentPrice, upperPrice)) {
            getContext().getParent().tell(SellCmd.create(dealOpenedInfo.positionId()), getSelf());
            getContext().stop(getSelf());
        }
    }
}
