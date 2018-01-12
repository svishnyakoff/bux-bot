package org.svishnyakov.bux.bot;

import org.asynchttpclient.ws.WebSocket;
import org.asynchttpclient.ws.WebSocketTextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.svishnyakov.bux.bot.event.AbstractEvent;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Accepts the messages from the socket connection and forwards the message to actor system for processing.
 */
public class EventDispatcherHandler implements WebSocketTextListener {

    private final Logger logger = LoggerFactory.getLogger(EventDispatcherHandler.class);

    private Consumer<AbstractEvent> onEventListener;
    private Consumer<Throwable> onErrorListener;
    private Consumer<WebSocket> onOpenListener;
    private Consumer<WebSocket> onClosedListener;

    @Override
    public void onMessage(String message) {
        try {
            AbstractEvent abstractEvent = Mapper.get().readValue(message, AbstractEvent.class);
            this.onEventListener.accept(abstractEvent);
        }
        catch (IOException e) {
            logger.warn("Cannot parse the event: {}", message, e);
        }
    }

    @Override
    public void onOpen(WebSocket websocket) {
        this.onOpenListener.accept(websocket);
    }

    @Override
    public void onClose(WebSocket websocket) {
        onClosedListener.accept(websocket);
    }

    @Override
    public void onError(Throwable t) {
        this.onErrorListener.accept(t);
    }

    public void setOnEventListener(Consumer<AbstractEvent> onEventListener) {
        this.onEventListener = onEventListener;
    }

    public void setOnErrorListener(Consumer<Throwable> onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    public void setOnOpenListener(Consumer<WebSocket> onOpenListener) {
        this.onOpenListener = onOpenListener;
    }

    public void setOnClosedListener(Consumer<WebSocket> onClosedListener) {
        this.onClosedListener = onClosedListener;
    }
}
