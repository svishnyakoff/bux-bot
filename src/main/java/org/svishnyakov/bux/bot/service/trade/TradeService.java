package org.svishnyakov.bux.bot.service.trade;

import java.util.UUID;

/**
 * Service that provides trading operations. If you want to open/closed the deal, then you probably would want to
 * use this
 */
public interface TradeService {

    /**
     * Open a deal for a given product.
     *
     * @param productId id of product
     * @return Provides context what and how much did you pay.
     * @throws TradeException when there was any exception during the trade.
     */
    DealOpened buyProduct(String productId) throws TradeException;

    /**
     * Close a deal for a given position.
     *
     * @param positionId id of position. You should get it when buy the product.
     * @return Provides context about selling price and your profit.
     * @throws TradeException when there was any exception during the trade.
     */
    DealClosed sell(UUID positionId) throws TradeException;
}
