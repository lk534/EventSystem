package dev.nova.zelos.bus;

import dev.nova.zelos.events.Event;
import dev.nova.zelos.listener.Listenable;

/**
 * The Event Bus allows you to subscribe objects
 * as well as post events that they can listen to.
 *
 * @see EventManager
 *
 * @author initiate_
 * @since 23/8/2021
 */
public interface EventBus {

    /**
     * Subscribe a Listenable object to your Bus.
     *
     * @param listenable The object you want to subscribe
     */
    void subscribe (Listenable listenable);

    /**
     * Unsubscribe a Listenable object from your Bus.
     *
     * @param listenable The object you want to unsubscribe
     */
    void unsubscribe (Listenable listenable);

    /**
     * Post an Event to you Bus.
     *
     * @param event The event you want to post to the bus
     */
    void post(Event event);

}
