package edu.byu.cs.vv.util;

import java.util.HashMap;

public class QueueMap<K, V> extends HashMap<K, TakeableQueue<V>> {

    public TakeableQueue<V> add(K key, V value) {
        if (!this.containsKey(key)) {
            this.put(key, new TakeableQueue<>());
        }
        this.get(key).add(value);
        return this.get(key);
    }

}
