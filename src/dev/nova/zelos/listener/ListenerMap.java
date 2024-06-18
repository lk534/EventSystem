package dev.nova.zelos.listener;

import java.util.HashMap;

public class ListenerMap extends HashMap<Integer, ListenerSet> {

    private int counter;

    public ListenerMap () {
        super();
        counter = 0;
    }

    /**
     * Insert a Listener into the Map.
     *
     * @param listener the listener
     * @return whether the listener could be inserted
     */
    public boolean insert (Listener listener) {
        if (!this.containsKey(listener.getPriority())) {
            ListenerSet set = new ListenerSet(ListenerSet.Type.ID);
            this.put(listener.getPriority(), set);
            return set.add(listener);
        }

        ListenerSet set = this.get(listener.getPriority());

        boolean success = set.insert(listener);

        if (success) counter++;

        return success;
    }


    /**
     * Eject a Listener out of the Map.
     *
     * @param listener the listener
     * @return whether the listener could be ejected
     */
    public boolean eject (Listener listener) {
        if (!this.containsKey(listener.getPriority())) return false;

        ListenerSet set = this.get(listener.getPriority());

        boolean success = set.eject(listener);

        if (success) counter--;

        return success;
    }

    public Listener[] getListeners () {
        Listener[] listeners = new Listener[counter +1];

        int count = 0;

        for (ListenerSet set: this.values()) {
            for (Listener listener: set) {
                listeners[count] = listener;
                count++;
            }
        }

        return listeners;
    }

}
