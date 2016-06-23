package edu.byu.cs.vv.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TakeableQueue<T> extends ArrayDeque<T> {

    public List<T> take(int n) {
        List<T> copies = new ArrayList<>();
        Iterator<T> current = this.iterator();
        while ((copies.size() < n) && (current.hasNext())) {
            copies.add(current.next());
        }
        return copies;
    }

}
