package org.svishnyakov.bux.bot.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.UUID;

public class AbstractEventDeserializer extends StdDeserializer<AbstractEvent> {

    protected AbstractEventDeserializer() {
        super(AbstractEvent.class);
    }

    @Override
    public AbstractEvent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        EventType type = null;
        Integer version = null;
        UUID id = null;
        Object body = null;

        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jp.getCurrentName();
            jp.nextToken();

            switch (fieldName) {
                case "t":
                    type = jp.readValueAs(EventType.class);
                    break;
                case "v":
                    version = jp.getNumberValue().intValue();
                    break;
                case "id":
                    id = jp.getCodec().readValue(jp, UUID.class);
                    break;
                case "body":
                    body = jp.readValueAs(type.getClazz());
                    break;
                default:
            }
        }

        return AbstractEvent.create(type, id, version, body);
    }
}
