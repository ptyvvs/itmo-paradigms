package queue;

public class LinkedQueue extends AbstractQueue implements Queue {
    private Node head;
    private Node tail;


    static class Node{
        private final Object value;
        private Node next;

        public Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    protected void enqueueImpl(Object element) {
        Node node = new Node(element, null);
        if (size == 0) {
            tail = head = node;
        } else {
           tail.next = node;
           tail = node;
        }
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected void dequeueImpl() {
        head = head.next;
    }


    protected void clearImpl() {
        head = new Node(null, null);
        tail = head;
    }

}
