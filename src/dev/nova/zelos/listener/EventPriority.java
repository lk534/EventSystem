package dev.nova.zelos.listener;

/**
 * Default values for various Event priorities
 * which a Listener can have. The higher the
 * priority, the less significant the Listener.
 *
 * @author initiate_
 * @since 23/8/2021
 */
public class EventPriority {
    /**
     * Highest - Use this for very important Listeners
     */
    public static final int HIGHEST = 0;
    /**
     * High - Use this for important Listeners
     */
    public static final int HIGH = 25;
    /**
     * Medium - Use this for ordinary Listeners
     */
    public static final int MEDIUM = 50;
    /**
     * Low - Use this for unimportant Listeners
     */
    public static final int LOW = 75;
    /**
     * Lowest - Use this for insignificant Listeners
     */
    public static final int LOWEST = 100;
}
