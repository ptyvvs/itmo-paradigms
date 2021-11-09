package queue;

public interface Queue {
    /*
    Model:
            {a0, a1, .., a(n - 1)}
            n -- queue's size


    INV:
            queue is in [0, n),
            forall i in [0, n): queue[i] != null
     */

    //PRED: INV
    //POST: queue[n] == element && n' == n + 1 && INV
    void enqueue(Object element);

    //PRED: INV && n > 0
    //POST: R = queue[n - 1] && queue is IMMUTABLE && INV
    Object element();

    //PRED: INV && n > 0
    //POST: R = elements[0] && INV
    Object dequeue();

    //PRED: INV
    //POST: R == n && queue is Immutable
    int size();

    //PRED: INV
    //POST: R == (n == 0) && queue is Immutable
    boolean isEmpty();

    //PRED: INV
    //POST: n == 0
    void clear();

    //PRED: INV
    //POST: R == Object[queue[0], queue[1], .., queue[n - 1]] && queue is IMMUTABLE && INV
    Object[] toArray();
}
