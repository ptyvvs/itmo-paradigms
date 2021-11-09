package queue;

public class MyArrayQueueModuleTest {
    public static void main(String[] args) {
        ArrayQueueModule queue = new ArrayQueueModule();
        fill(queue);
        dump(queue);
    }

    private static void fill(ArrayQueueModule queue) {
        for (int i = 0; i < 9; i++) {
            ArrayQueueModule.enqueue(i);
        }
        //System.out.println(ArrayQueueADT.element(queue));
    }

    private static void dump(ArrayQueueModule queue) {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.dequeue());
        }
    }
}
