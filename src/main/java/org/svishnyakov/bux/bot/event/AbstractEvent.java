package org.svishnyakov.bux.bot.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import java.util.UUID;

@JsonDeserialize(using = AbstractEventDeserializer.class)
@AutoValue
public abstract class AbstractEvent {

    @JsonCreator
    public static AbstractEvent create(@JsonProperty("t") EventType t,
                                       @JsonProperty("id") UUID id,
                                       @JsonProperty("version") Integer version,
                                       @JsonProperty("body") Object body) {
        return new AutoValue_AbstractEvent(t, id, version, body);
    }

    public abstract EventType t();

    public abstract UUID id();

    public abstract Integer version();

    public abstract Object body();
}
