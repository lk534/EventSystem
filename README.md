# Zelos
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Simple Event system library for Java which prioritizes posting performance over memory usage.

## Usage

The following example shows how to instantiate an EventBus object and post events to it:

```java
package dev.nova.app;

import dev.nova.zelos.bus.EventBus;
import dev.nova.zelos.bus.EventManager;

public class Main {
    
    public static void main(String[] args) {
       EventBus bus = new EventManager();
       
       bus.post(new Event());
    }
    
}
```

To create custom events extend the Event class or CancellableEvent class:

```java
package dev.nova.app;

public class DamageEvent extends Event {

    private int damage;

    public DamageEvent(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
    
}
```

To listen to events, your class must implement EventListener. Then, any methods with the annotation @EventHandler will be executed when the event in the parameter is posted to the manager if the object is subscribed:

```java
package dev.nova.app;

import dev.nova.zelos.listener.EventHandler;
import dev.nova.zelos.listener.Listenable;

public class Player implements Listenable {

    private int health;

    public Player(int health) {
        this.health = health;
    }

    @EventHandler
    public void onDamage(DamageEvent event) {
        this.health = this.health - event.getDamage();
    }

}
```

To subscribe and unsubscribe listeners, call the respective method using the EventManager object. Subscribed listeners will listen to events while a listener can be unsubscribed to stop listening to events:

```java
package dev.nova.app;

import dev.nova.zelos.bus.EventBus;
import dev.nova.zelos.bus.EventManager;

public class Main {

    public static void main(String[] args) {
        EventBus bus = new EventManager();

        Player player = new Player(30);

        bus.subscribe(player);

        bus.post(new DamageEvent(10));

        bus.unsubscribe(player);
    }
    
}
```

If you want your listener to have higher priority over other listeners use the priority parameter of the @EventHandler annotation.

```java
package dev.nova.app;

import dev.nova.zelos.listener.EventHandler;
import dev.nova.zelos.listener.EventPriority;
import dev.nova.zelos.listener.Listenable;

public class Bot implements Listenable {

    private int health;

    public Bot(int health) {
        Main.bus.subscribe(this);
        this.health = health;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(DamageEvent event) {
        this.health = this.health - event.getDamage();
    }

}
```