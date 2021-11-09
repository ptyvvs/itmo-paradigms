package queue;

public class ArrayQueue extends AbstractQueue implements Queue {
    private Object[] elements = new Object[2];
    private int head = 0;
    private int tail = 0;

    public ArrayQueue() {
    }

    protected void enqueueImpl(Object element) {
        ensureCapacity(size() + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }


    private void ensureCapacity(int capacity) {
        if (capacity == elements.length) {
            Object[] changedElements = new Object[capacity * 2];
            System.arraycopy(toArray(), 0, changedElements, 0, size);
            elements = changedElements;
            head = 0;
            tail = size;
        }
    }

    protected Object elementImpl() {
        return elements[head];
    }

    protected void dequeueImpl() {
        elements[head] = null;
        head = (head + 1) % elements.length;
    }


    public void clearImpl() {
        elements = new Object[2];
        head = 0;
        tail = 0;
    }

}
