package org.svishnyakov.bux.bot.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.util.UUID;

@AutoValue
public abstract class ConnectedEvent implements Event {

    @JsonCreator
    public static ConnectedEvent create(@JsonProperty("sessionId") UUID sessionId,
                                        @JsonProperty("time") long time,
                                        @JsonProperty("clientVersion") String clientVersion) {
        return new AutoValue_ConnectedEvent(sessionId, time, clientVersion);
    }

    public abstract UUID sessionId();
    public abstract long time();
    public abstract String clientVersion();
}
