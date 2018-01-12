package org.svishnyakov.bux.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Mapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private Mapper() {
    }

    public static ObjectMapper get() {
        return OBJECT_MAPPER;
    }

    public static String writeValueAsString(Object message) {
        try {
            return get().writeValueAsString(message);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
