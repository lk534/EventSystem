package dev.nova.zelos.events;


/**
 * A type of Event that can be cancelled.
 *
 * @see Event
 *
 * @author initiate_
 * @since 23/8/2021
 */
public class Cancellable extends Event {
    private boolean cancelled;

    /**
     * Checks if the Event is cancelled
     *
     * @return event cancelled state
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancelled state of the Event.
     *
     * @param cancelled event cancelled state
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Cancels the Event.
     */
    public void cancel () {
        cancelled = true;
    }
}
