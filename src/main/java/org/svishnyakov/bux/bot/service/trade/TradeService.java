package org.svishnyakov.bux.bot.service.trade;

import java.util.UUID;

public interface TradeService {

    DealOpened buyProduct(String productId) throws TradeException;

    DealClosed sell(UUID positionId) throws TradeException;
}
