package edu.byu.cs.vv.util;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class QueueMap<K, V> extends HashMap<K, Queue<V>> {

    public Queue<V> add(K key, V value) {
        if (!this.containsKey(key)) {
            this.put(key, new ArrayDeque<>());
        }
        this.get(key).add(value);
        return this.get(key);
    }

}
