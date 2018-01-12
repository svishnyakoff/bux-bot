package org.svishnyakov.bux.bot.event;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

/**
 * Type of event that would describe the class we should use for body object.
 * For new or unknown event the UNKNOWN will be used.
 */
public enum EventType {

    CONNECTED("connect.connected", ConnectedEvent.class),
    TRADING_QUOTE("trading.quote", TradingQuoteEvent.class),
    UNKNOWN("unknown", UnknownEvent.class);

    private String id;
    private Class clazz;

    EventType(String id, Class clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    @JsonCreator
    public static EventType of(String id) {
        return Stream.of(EventType.values())
                .filter(eventType -> eventType.id.equals(id))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public Class getClazz() {
        return this.clazz;
    }

    public String getId() {
        return this.id;
    }
}
