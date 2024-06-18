package dev.nova.zelos.bus;

import dev.nova.zelos.events.Event;
import dev.nova.zelos.listener.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * The Event Manager is an implementation of the Event Bus.
 *
 * @author initiate_
 * @since 23/8/2021
 */
public class EventManager implements EventBus {

    public int counter = 0;

    /**
     * Map containing all the Listeners subscribed to each Event.
     */
    private Map<Class<?>, ListenerSet> eventBinds;

    /**
     * Map containing all cached Listeners belonging to each Listenable object.
     */
    private Map<Listenable, ListenerMap> listenerCache;

    /**
     * Instantiates a new Event manager.
     */
    public EventManager () {
        this.eventBinds = new HashMap<>();
        this.listenerCache = new HashMap<>();
    }

    @Override
    public void subscribe (Listenable listenable) {

        boolean isCached = listenerCache.containsKey(listenable);

        if (!isCached) {
            ListenerMap map = new ListenerMap();

            Class<?> listenableClass = listenable.getClass();

            for (Method method: listenableClass.getMethods()) {

                if (!method.isAnnotationPresent(EventHandler.class)) continue;

                if (method.getParameterCount() != 1) return;

                Class<?> eventClass = method.getParameterTypes()[0];

                if(!Event.class.isAssignableFrom(eventClass)) return;

                int priority = method.getAnnotation(EventHandler.class).priority();

                Listener listener = new Listener(this, listenable, method, eventClass, priority);

                map.insert(listener);
            }

            listenerCache.put(listenable, map);
        }

        for (Listener listener: listenerCache.get(listenable).getListeners()) {
            Class<?> eventClass = listener.getEvent();

            if (!eventBinds.containsKey(eventClass)) eventBinds.put(eventClass, new ListenerSet(ListenerSet.Type.PRIORITY));

            ListenerSet set = eventBinds.get(eventClass);

            if (!set.insert(listener)) return;
        }

    }

    @Override
    public void unsubscribe (Listenable listenable) {

        if (!listenerCache.containsKey(listenable)) return;

        for (Listener listener: listenerCache.get(listenable).getListeners()) {
            Class<?> eventClass = listener.getEvent();

            ListenerSet set = eventBinds.get(eventClass);

            if (!set.eject(listener)) return;
        }

    }


    @Override
    public void post (Event event) {

        Class<?> eventClass = event.getClass();

        if (!eventBinds.containsKey(eventClass)) return;

        ListenerSet set = eventBinds.get(eventClass);

        for (Listener listener: set) {
            try {
                listener.getMethod().invoke(listener.getListenable(), event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

}

