package dev.nova.zelos.listener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An extension of the Java ArrayList. Listeners are
 * automatically sorted by their date created/updated
 * or priority and duplicate Listeners are prevented.
 *
 * @see ArrayList
 * @see Listener
 *
 * @author initiate_
 * @since 23/8/2021
 */
public class ListenerSet extends ArrayList<Listener> {

    private Type type;

    public ListenerSet (Type type) {
        this.type = type;
    }

    /**
     * Insert a Listener into the Set.
     *
     * @param listener the listener
     * @return whether the listener could be inserted
     */
    public boolean insert (Listener listener) {
        int position = search(listener);

        if (this.size() > position && this.get(position) == listener) return false;

        if (!super.add(listener)) return false;

        if (this.type == Type.PRIORITY) for (int i = this.size() - 1; i > position; i--) Collections.swap(this, i, i-1);
        else listener.update();

        return true;
    }


    /**
     * Eject a Listener out of the Set.
     *
     * @param listener the listener
     * @return whether the listener could be ejected
     */
    public boolean eject (Listener listener) {
        int position = search(listener);

        if (this.get(position) == listener) {
            super.remove(position);
            return true;
        }

        else return false;
    }

    /**
     * Binary search algorithm which finds where the Listener should be.
     *
     * @param listener the listener
     * @return where the listener should be located
     */
    private int search (Listener listener) {

        if (this.size() == 0) return 0;

        boolean found = false;

        int min = 0;
        int max = this.size() - 1;
        int mid = 0;

        while (min <= max) {
            mid = (min + max) / 2;

            Listener midListener = this.get(mid);

            if (getValue(listener) == getValue(midListener)) {
                found = true;
                break;
            }

            else if (getValue(listener) > getValue(midListener)) min = mid + 1;

            else max = mid - 1;
        }

        if (!found) {
            if (min>max) return min;
            else return max;
        }

        int temp = mid;

        while (temp >= 0 && getValue(this.get(temp)) == getValue(listener)) {
            if (this.get(temp) == listener) return temp;
            temp--;
        }

        temp = mid + 1;

        while (temp < this.size() && getValue(this.get(temp)) == getValue(listener)) {
            if (this.get(temp) == listener) return temp;
            temp++;
        }

        return mid;

    }

    private int getValue (Listener listener) {
        if (type == Type.ID) return listener.getId();
        return listener.getPriority();
    }

    public enum Type {
        PRIORITY, ID
    }
}

