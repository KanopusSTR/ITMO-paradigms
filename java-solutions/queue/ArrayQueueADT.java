package queue;

/*
Model: a[0]..a[n-1]
Invariant: for i = 0..n-1: a[i] != null

Pred: element != null && queue != null
Post: n' = n + 1 && a[n] = element
    enqueue(queue, element)

Pred: n >= 1 && queue != null
Post: R = a[0] && n' = n && for i = 0..n-1: a'[i] = a[i]
    element(queue)

Pred: queue != null
Post: a = for i = 0..n-1: a'[i] = null && n' = 0
clear(queue)

Pred: n >= 1 && queue != null
Post: n' = n - 1 && for i = 0..n'-1 : a'[i] = a[i + 1]
dequeue(queue)

Pred: element != null && queue != null
Post: min i : a[i] == element || -1
indexOf(queue, element)

Pred: element != null && queue != null
Post: max i : a[i] == element || -1
lastIndexOf(queue, element)
*/

public class ArrayQueueADT {
    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[5];

//    Pred: element != null && queue != null
//    Post: n' = n + 1 && a[n] = element
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;
        ensureCapacity(queue, queue.size + 1);
        queue.size++;
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
    }

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity >= queue.elements.length) {
            Object[] element_s = new Object[2 * queue.elements.length];
            if (queue.tail >= queue.head) {
                System.arraycopy(queue.elements, 0, element_s, 0, queue.size);
            } else {
                System.arraycopy(queue.elements, queue.head, element_s, 0, queue.size - queue.head + 1);
                System.arraycopy(queue.elements, 0, element_s, queue.size - queue.head + 1, queue.head);
                queue.head = 0;
                queue.tail = queue.size;
            }
            queue.elements = element_s;
        }
    }

//    Pred: n >= 1 && queue != null
//    Post: n' = n - 1 && for i = 0..n'-1 : a'[i] = a[i + 1]
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        queue.size--;
        Object x = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        return x;
    }

//    Pred: n >= 1 && queue != null
//    Post: R = a[0] && n' = n && for i = 0..n-1: a'[i] = a[i]
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

//    Pred: queue != null
//    Post: R = n && for i = 0..n-1: a'[i] = a[i]
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

//    Pred: true
//    Post: size == n && for i = 0..n-1: a'[i] = a[i]
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

//    Pred: true
//    Post: a = for i = 0..n-1: a'[i] = null && head = 0 && tail = 0 && size = 0
    public static void clear(ArrayQueueADT queue) {
        while (((queue.tail - queue.head) % queue.elements.length) >= 0) {
            queue.elements[queue.tail--] = null;
        }
        queue.head = 0;
        queue.tail = 0;
        queue.size = 0;
    }

//    Pred: element != null && queue != null
//    Post: min i : a[i] == element || -1
    public static int indexOf(ArrayQueueADT queue, Object element) {
        int i = 0;
        while (i < queue.size) {
            if (queue.elements[(i + queue.head) % queue.elements.length].equals(element)) {
                return i;
            }
            i++;
        }
        return -1;
    }

//    Pred: element != null && queue != null
//    Post: max i : a[i] == element || -1
    public static int lastIndexOf(ArrayQueueADT queue, Object element) {
        int i = 0;
        while (i < queue.size) {
            if (queue.elements[(queue.tail - i - 1 + queue.elements.length) % queue.elements.length].equals(element)) {
                return queue.size - i - 1;
            }
            i++;
        }
        return -1;
    }
}
