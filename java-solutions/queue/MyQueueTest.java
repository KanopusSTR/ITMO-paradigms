package queue;

import java.util.function.Predicate;

public class MyQueueTest {

    public static void fill(Queue queue) {
        for (int i = 0; i < 6; i++) {
            queue.enqueue(i);
        }
        queue.enqueue(5);
        Predicate<Object> test = value->value.equals(5);
        System.out.println(queue.indexIf(test));
        System.out.println(queue.lastIndexIf(test));
    }

    public static void dump(Queue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.element() + queue.dequeue());
        }
    }

    public static void main(String[] args) {
        Queue arQueue = new ArrayQueue();
        fill(arQueue);
//        dump(arQueue);

        System.out.println("----");

        Queue lQueue = new LinkedQueue();
        fill(lQueue);
//        dump(lQueue);
    }
}
