package org.svishnyakov.bux.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleLogger {

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleLogger.class);

    public static void log(String message, Object... args) {
        LOG.info(message, args);
    }
}
