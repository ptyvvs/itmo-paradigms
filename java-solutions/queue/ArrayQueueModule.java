package queue;

public class ArrayQueueModule {

    private static Object[] elements = new Object[2];
    private static int head = 0;
    private static int tail = 0;
      /*INV:
        queue is in [0, n),
        forall i in [0, n): queue[i] != null
     */

    //PRED: INV
    public static void enqueue(Object element) {
        ensureCapacity(size() + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }
    //POST: queue[n'] == element && n' == n + 1 && INV

    private static void ensureCapacity(int capacity) {
        int size = size();
        if (capacity == elements.length) {
            Object[] changedElements = new Object[capacity * 2];
            System.arraycopy(toArray(), 0, changedElements, 0, size);
            elements = changedElements;
            head = 0;
            tail = size;
        }
    }

    //PRED: INV && n > 0
    public static Object element() {
        assert size() > 0;
        return elements[head];
    }
    //POST: R = queue[n - 1] && queue is IMMUTABLE && INV

    //PRED: INV && n > 0
    public static Object dequeue() {
        Object result = element();
        elements[head] = null;
        head = (head + 1) % elements.length;
        return result;
    }
    //POST: R = elements[0] && INV

    //PRED: INV
    public static int size() {
        if (tail >= head) {
            return tail - head;
        } else {
            return elements.length - head + tail;
        }
    }
    //POST: R == n && queue is Immutable

    //PRED: INV
    public static boolean isEmpty() {
        return size() == 0;
    }
    //POST: R == (n == 0) && queue is Immutable


    //PRED: INV
    public static void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
        head = 0;
        tail = 0;
    }
    //POST: n == 0

    //PRED: INV

    public static Object[] toArray() {
        Object[] result = new Object[size()];
        if (tail >= head) {
            System.arraycopy(elements, head, result, 0, size());
        } else {
            System.arraycopy(elements, head, result, 0, elements.length - head);
            System.arraycopy(elements, 0, result, elements.length - head, tail);
        }
        return result;
    }
    //POST: R == Object[queue[0], queue[1], .., queue[n - 1]] && queue is IMMUTABLE && INV

}
