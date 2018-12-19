package ru.job4j.json;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores objects.
 *
 * @param <T> genetic object type.
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Storage<T> {
    /**
     * Storage of objects.
     */
    private final ConcurrentHashMap<T, Object> store = new ConcurrentHashMap<>();
    /**
     * Dummy object to store in map.
     */
    private final Object dummy = new Object();

    /**
     * Adds new object to storage.
     *
     * @param object Object to store.
     */
    public void add(T object) {
        this.store.put(object, this.dummy);
        System.out.println(String.format("Added object: %s. Objects in storage: %s", object, this.store.size()));
    }

}
