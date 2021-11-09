package queue;

public class MyArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        fill(queue);
        dump(queue);
    }

    private static void fill(ArrayQueue queue) {
        for (int i = 0; i < 9; i++) {
            queue.enqueue(i);
        }
        //System.out.println(ArrayQueueADT.element(queue));
    }

    private static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }
}
