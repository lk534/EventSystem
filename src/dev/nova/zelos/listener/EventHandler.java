package dev.nova.zelos.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which marks methods listening for Events.
 *
 * @author initiate_
 * @since 23/8/2021
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    /**
     * Priority of the Listener when an Event is posted.
     *
     * @return the Event priority
     */
    int priority() default EventPriority.MEDIUM;
}
