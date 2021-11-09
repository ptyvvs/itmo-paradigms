package queue;

public class ArrayQueueADT {

    /*INV:
        queue is in [0, n),
        forall i in [0, n): queue[i] != null
     */

    private Object[] elements = new Object[2];
    private int head = 0;
    private int tail = 0;

    //PRED: INV
    public static void enqueue(ArrayQueueADT queue, Object element) {
        ensureCapacity(queue, size(queue) + 1);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
    }
    //POST: queue[n'] == element && n' == n + 1 && INV

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        int size = size(queue);
        if (capacity == queue.elements.length) {
            Object[] changedElements = new Object[capacity * 2];
            System.arraycopy(toArray(queue), 0, changedElements, 0, size);
            queue.elements = changedElements;
            queue.head = 0;
            queue.tail = size;
        }
    }

    //PRED: INV && n > 0
    public static Object element(ArrayQueueADT queue) {
        assert size(queue) > 0;
        return queue.elements[queue.head];
    }
    //POST: R = queue[n - 1] && queue is IMMUTABLE && INV

    //PRED: INV && n > 0
    public static Object dequeue(ArrayQueueADT queue) {
        Object result = element(queue);
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        return result;
    }
    //POST: R = elements[0] && INV

    //PRED: INV
    public static int size(ArrayQueueADT queue) {
        if (queue.tail >= queue.head) {
            return queue.tail - queue.head;
        } else {
            return queue.elements.length - queue.head + queue.tail;
        }
    }
    //POST: R == n && queue is Immutable


    //PRED: INV
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }
    //POST: R == (n == 0) && queue is Immutable


    //PRED: INV
    public static void clear(ArrayQueueADT queue) {
        for (int i = 0; i < queue.elements.length; i++) {
            queue.elements[i] = null;
        }
        queue.head = 0;
        queue.tail = 0;
    }
    //POST: n == 0


    //PRED: INV

    public static Object[] toArray(ArrayQueueADT queue) {
        Object[] result = new Object[size(queue)];
        if (queue.tail >= queue.head) {
            System.arraycopy(queue.elements, queue.head, result, 0, size(queue));
        } else {
            System.arraycopy(queue.elements, queue.head, result, 0, queue.elements.length - queue.head);
            System.arraycopy(queue.elements, 0, result, queue.elements.length - queue.head, queue.tail);
        }
        return result;
    }
    //POST: R == Object[queue[0], queue[1], .., queue[n - 1]] && queue is IMMUTABLE && INV

}
