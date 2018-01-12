package org.svishnyakov.bux.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use whenever you want to print messages that would be useful for user.
 *
 * The existing logger configuration will ignore all the INFO messages, but from this class.
 */
public final class ConsoleLogger {

    private ConsoleLogger() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleLogger.class);

    public static void log(String message, Object... args) {
        LOG.info(message, args);
    }
}
