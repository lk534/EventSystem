package dev.nova.app;

import dev.nova.zelos.bus.EventBus;
import dev.nova.zelos.bus.EventManager;
import dev.nova.zelos.events.Cancellable;
import dev.nova.zelos.listener.EventHandler;
import dev.nova.zelos.listener.Listenable;


public class Test2023 {
    public static class RenderEvent extends Cancellable {
        private int id;

        public RenderEvent(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static class Window implements Listenable {
        private int id;

        public Window(int id) {
            this.id = id;
        }

        @EventHandler
        public void onRender (RenderEvent event) {
            System.out.println("Rendering event " + event.getId() + " from window " + id);
        }
    }
    public static void main(String[] args) {
        EventBus bus = new EventManager();

        Window window = new Window(45);
        bus.subscribe(window);

        bus.post(new RenderEvent(1));

        Window window1 = new Window(58);
        bus.subscribe(window1);

        bus.post(new RenderEvent(2));

    }
}
