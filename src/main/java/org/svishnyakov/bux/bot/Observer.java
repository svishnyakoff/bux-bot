package org.svishnyakov.bux.bot;

import org.svishnyakov.bux.bot.event.AbstractEvent;

public interface Observer {

    void receive(AbstractEvent event);
}
