package dev.nova.zelos.listener;

import dev.nova.zelos.bus.EventManager;

import java.lang.reflect.Method;

/**
 * The Listener object stores data relevant to an Event listening method.
 *
 * @author initiate_
 * @since 23/8/2021
 */
public class Listener {

    private Listenable listenable;

    private Method method;

    private Class<?> event;

    private EventManager manager;

    private int id;

    private int priority;

    /**
     * Instantiates a new Listener.
     *
     * @param listenable the listenable object
     * @param method     the listening method
     * @param event      the event type
     * @param priority   the event priority
     */
    public Listener (EventManager manager, Listenable listenable, Method method, Class<?> event, int priority) {
        this.manager = manager;
        this.listenable = listenable;
        this.method = method;
        this.event = event;
        this.priority = priority;
        this.update();
    }

    /**
     * Gets the Listenable object which is parent of the Listener.
     *
     * @return the listenable object
     */
    public Listenable getListenable() {
        return listenable;
    }

    /**
     * Gets the Event listening method linked to Listener.
     *
     * @return the method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Gets the priority of the Event Listener.
     *
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gets the event type the Listener is subscribed to.
     *
     * @return the event class
     */
    public Class<?> getEvent() {
        return event;
    }

    public int getId() {
        return id;
    }

    public void update () {
        this.id = this.manager.counter;
        this.manager.counter++;
    }
}
