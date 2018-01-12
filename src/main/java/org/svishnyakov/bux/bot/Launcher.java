package org.svishnyakov.bux.bot;

import akka.actor.ActorSystem;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ws.WebSocketUpgradeHandler;
import org.svishnyakov.bux.bot.actors.LeaderActor;
import org.svishnyakov.bux.bot.service.trade.HttpTradeService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class Launcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException, ParseException, IOException {

        CommandLineParser commandLineParser = new DefaultParser();

        Options options = new Options();
        options.addRequiredOption("i", "id", true, "The product id");
        options.addRequiredOption("p", "price", true, "The buy price");
        options.addRequiredOption("l", "lower", true, "The lower limit sell price this the price you want are " +
                "willing to close a position at and make a loss.");
        options.addRequiredOption("u", "upper", true, "The upper limit sell price this is the price you are " +
                "willing to close a position and make a profit.");

        BotArgs botArgs;
        try {
            CommandLine cli = commandLineParser.parse(options, args);
            botArgs = BotArgs.create(cli.getOptionValue("i"),
                                     new BigDecimal(cli.getOptionValue("p")),
                                     new BigDecimal(cli.getOptionValue("l")),
                                     new BigDecimal(cli.getOptionValue("u")));
        }
        catch (ParseException e) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("trading-bot", options, true);
            return;
        }

        ActorSystem actorSystem = ActorSystem.create();

        EventDispatcherHandler eventDispatcherHandler = new EventDispatcherHandler();

        eventDispatcherHandler.setOnOpenListener(webSocket -> {
            actorSystem.actorOf(LeaderActor.props(botArgs, webSocket, new HttpTradeService()), "leader");
        });

        eventDispatcherHandler.setOnEventListener(event -> {
            actorSystem.actorSelection("/user/leader").tell(event.body(), null);
        });

        eventDispatcherHandler.setOnClosedListener(webSocket -> {
            actorSystem.terminate();
            countDownLatch.countDown();
        });

        try (AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient()) {
            asyncHttpClient.prepareGet(ConnectionConfig.getSubscriptionUrl())
                    .addHeader("Authorization", ConnectionConfig.getSecuredHeader())
                    .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(eventDispatcherHandler).build()).get();
            countDownLatch.await();
        }
    }
}
