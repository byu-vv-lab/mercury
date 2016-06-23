package edu.byu.cs.vv.util;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;

@Category(edu.byu.cs.vv.categories.Utils.class)
public class TakeableQueueTest {

    @Test
    public void take() throws Exception {
        TakeableQueue<Integer> queue = new TakeableQueue<>();
        Integer[] array = new Integer[4];
        for (int i = 0; i < 4; i++) {
            queue.add(i);
            array[i] = i;
        }
        assertEquals(1, queue.take(1).size());
        assertEquals(4, queue.take(4).size());
        assertArrayEquals(array, queue.take(4).toArray());
        assertEquals(4, queue.take(8).size());
        assertArrayEquals(array, queue.take(8).toArray());
    }

}