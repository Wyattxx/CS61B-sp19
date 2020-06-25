package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void testAdd() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 2);
        minHeap.add(6, 3);
        assertTrue(minHeap.size() == 6);
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 2);
        minHeap.add(6, 3);
        assertTrue(minHeap.contains(2));
        assertFalse(minHeap.contains(0));
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 0);
        minHeap.add(6, 3);
        assertTrue(minHeap.getSmallest() == 5);
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 0);
        minHeap.add(6, 3);
        assertTrue(minHeap.removeSmallest() == 5);
        assertTrue(minHeap.getSmallest() == 1);
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        minHeap.add(1, 1);
        minHeap.add(2, 2);
        minHeap.add(3, 3);
        minHeap.add(4, 4);
        minHeap.add(5, 0);
        minHeap.add(6, 3);
        minHeap.changePriority(5, 5);
        minHeap.changePriority(6, 0);
        assertTrue(minHeap.getSmallest() == 6);
    }

    public static void main(String[] args) {

        long start1 = System.currentTimeMillis();
        ExtrinsicMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 200000; i += 1) {
            minHeap.add(i, 100000 - i);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end1 - start1) / 1000.0 +  " seconds.");

        long start2 = System.currentTimeMillis();
        for (int j = 0; j < 200000; j += 1) {
            minHeap.changePriority(j, j + 1);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end2 - start2) / 1000.0 +  " seconds.");


        long start3 = System.currentTimeMillis();
        ExtrinsicMinPQ<Integer> minHeapB = new NaiveMinPQ<>();
        for (int i = 0; i < 200000; i += 1) {
            minHeapB.add(i, 100000 - i);
        }
        long end3 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end3 - start3) / 1000.0 +  " seconds.");

        long start4 = System.currentTimeMillis();
        for (int j = 0; j < 200000; j += 1) {
            minHeapB.changePriority(j, j + 1);
        }
        long end4 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end4 - start4) / 1000.0 +  " seconds.");
    }
}