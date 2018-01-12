package org.svishnyakov.bux.bot.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.asynchttpclient.ws.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.svishnyakov.bux.bot.BotArgs;
import org.svishnyakov.bux.bot.ConsoleLogger;
import org.svishnyakov.bux.bot.Mapper;
import org.svishnyakov.bux.bot.command.BuyCmd;
import org.svishnyakov.bux.bot.command.SellCmd;
import org.svishnyakov.bux.bot.command.SubscribeCmd;
import org.svishnyakov.bux.bot.event.ConnectedEvent;
import org.svishnyakov.bux.bot.event.Event;
import org.svishnyakov.bux.bot.service.trade.DealClosed;
import org.svishnyakov.bux.bot.service.trade.DealOpened;
import org.svishnyakov.bux.bot.service.trade.HttpTradeService;

import java.io.IOException;
import java.util.UUID;

/**
 * Main actor that controls other actors. The child actor should signal it when it's a good time to buy or sell.
 */
public class LeaderActor extends AbstractActor {

    private static final Logger LOG = LoggerFactory.getLogger(LeaderActor.class);

    private final HttpTradeService tradeService;
    private final BotArgs botArgs;
    private final WebSocket webSocket;
    private UUID positionId;

    public LeaderActor(BotArgs botArgs, WebSocket webSocket, HttpTradeService tradeService) {
        this.botArgs = botArgs;
        this.webSocket = webSocket;
        this.tradeService = tradeService;
    }

    public static Props props(BotArgs botArgs, WebSocket webSocket, HttpTradeService tradeService) {
        return Props.create(LeaderActor.class, botArgs, webSocket, tradeService);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ConnectedEvent.class, connect -> subscribe())
                .match(Event.class, this::delegateToChild)

                .match(BuyCmd.class, this::onBuyProduct)
                .match(SellCmd.class, this::onSell)
                .matchAny(this::delegateToChild)
                .build();
    }

    private void onSell(SellCmd sellCmd) {
        DealClosed dealClosed = tradeService.sell(sellCmd.getPositionId());

        ConsoleLogger.log("Sell action: Product [{}], sold with price [{}]. The profit {} {}",
                          dealClosed.product().displayName(), dealClosed.price().amount(),
                          dealClosed.profitAndLoss().amount(), dealClosed.profitAndLoss().currency());
        stopSystem();

    }

    private void subscribe() {
        ConsoleLogger.log("Subscribing to product {}", botArgs.productId());
        SubscribeCmd subscribeCommand = SubscribeCmd.create(botArgs.productId());
        sendMessage(subscribeCommand);
        getContext().actorOf(PriceWatchdogActor.props(botArgs.productId(), botArgs.buyPrice(), botArgs.lowerSellPrice()));
    }

    private void onBuyProduct(BuyCmd buyCmd) {
        DealOpened dealOpened = tradeService.buyProduct(buyCmd.getProductId());
        ConsoleLogger.log("Buy action: Product [{}], price [{} {}]", dealOpened.product().displayName(),
                          dealOpened.price().amount(), dealOpened.price().currency());
        positionId = dealOpened.positionId();
        getContext().actorOf(DealCloserActor.props(botArgs.productId(), dealOpened, botArgs.lowerSellPrice(),
                                                   botArgs.upperSellPrice()));
    }

    private void delegateToChild(Object event) {
        for (ActorRef child : getContext().getChildren()) {
            child.tell(event, getSelf());
        }
    }

    private void sendMessage(Object message) {
        webSocket.sendMessage(Mapper.writeValueAsString(message));
    }


    @Override
    public void postStop() throws Exception {
        super.postStop();
        if (positionId != null) {
            tradeService.sell(positionId);
        }
    }

    private void stopSystem() {
        try {
            getContext().system().terminate();
            webSocket.close();
        }
        catch (IOException e) {
            LOG.error("Failed to close webSocket", e);
        }
    }
}
