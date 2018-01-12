package org.svishnyakov.bux.bot.command;

import com.google.auto.value.AutoValue;

import java.util.UUID;

/**
 * Request to sell given position.
 */
@AutoValue
public abstract class SellCmd {
    public static SellCmd create(UUID positionId) {
        return new AutoValue_SellCmd(positionId);
    }

    public abstract UUID getPositionId();
}
