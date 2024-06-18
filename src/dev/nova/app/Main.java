package dev.nova.app;

import dev.nova.zelos.bus.EventBus;
import dev.nova.zelos.bus.EventManager;
import dev.nova.zelos.events.Event;
import dev.nova.zelos.listener.EventHandler;
import dev.nova.zelos.listener.Listenable;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        delta();

        long average = 0;
        for (int i = 0; i < 10; i++) {
            long current = delta();
            System.out.println(current);
            average += current;
        }
        average /= 10;
        System.out.println("AVERAGE: " + average);
    }

    public static long delta () {

        long date1 = System.currentTimeMillis();

        EventBus bus = new EventManager();

        ArrayList<L> listeners = new ArrayList<>();

        // Create Listeners
        for (int i = 0; i < 2000; i++) {
            int priority = (int) (Math.random() * 100);

            L l = new L();

            if (priority >= 80) l = new L4();
            else if (priority >= 60) l = new L3();
            else if (priority >= 40) l = new L2();
            else if (priority >= 20) l = new L1();

            listeners.add(l);

            bus.subscribe(l);
        }

        // Post Events
        for (int i = 0; i < 15000; i++) {
            bus.post(new Event());
        }

        // Unsubscribe some
        for (int i = 0; i < 1000; i++) {
            bus.unsubscribe(listeners.get(i));
        }

        // Post Events
        for (int i = 0; i < 15000; i++) {
            bus.post(new Event());
        }

//        // Output
//        for (L l: listeners) {
//            System.out.println(l.runCount);
//        }

        long date2 = System.currentTimeMillis();

        return (date2 - date1);
    }

    public static class L implements Listenable {
        public int runCount = 0;
        @EventHandler(priority = 0)
        public void onEvent(Event event) {
            runCount++;
        }
    }

    public static class L1 extends L {
        @EventHandler(priority = 25)
        public void onEvent(Event event) {
            runCount++;
        }
    }

    public static class L2 extends L {
        protected String name = "l2";

        @EventHandler(priority = 50)
        public void onEvent(Event event) {
            runCount++;
        }
    }


    public static class L3 extends L {
        @EventHandler(priority = 75)
        public void onEvent(Event event) {
            runCount++;
        }
    }

    public static class L4 extends L {
        @EventHandler(priority = 100)
        public void onEvent(Event event) {
            runCount++;
        }
    }
}