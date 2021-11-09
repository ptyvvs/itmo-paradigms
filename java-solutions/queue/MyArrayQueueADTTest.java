package queue;

public class MyArrayQueueADTTest {
    public static void main(String[] args) {
        ArrayQueueADT queue = new ArrayQueueADT();
        fill(queue);
        dump(queue);
    }

    private static void fill(ArrayQueueADT queue) {
        for (int i = 0; i < 9; i++) {
            ArrayQueueADT.enqueue(queue, i);
        }
        //System.out.println(ArrayQueueADT.element(queue));
    }

    private static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.dequeue(queue));
        }
    }
}
