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

public class ArrayQueueModule {

    private static int size;
    private static int head;
    private static int tail;
    private static Object[] elements = new Object[5];

//    Pred: element != null && queue != null
//    Post: n' = n + 1 && a[n] = element
    public static void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        size++;
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    private static void ensureCapacity(int capacity) {
        if (capacity >= elements.length) {
            Object[] element_s = new Object[2 * elements.length];
            if (tail >= head) {
                System.arraycopy(elements, 0, element_s, 0, size);
            } else {
                System.arraycopy(elements, head, element_s, 0, size - head + 1);
                System.arraycopy(elements, 0, element_s, size - head + 1, head);
                head = 0;
                tail = size;
            }
            elements = element_s;
        }
    }

//    Pred: n >= 1 && queue != null
//    Post: R = a[0] && n' = n && for i = 0..n-1: a'[i] = a[i]
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

//    Pred: n >= 1 && queue != null
//    Post: n' = n - 1 && for i = 0..n'-1 : a'[i] = a[i + 1]
    public static Object dequeue() {
        assert size > 0;
        size--;
        Object x = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return x;
    }

//    Pred: true
//    Post: R = n && for i = 0..n-1: a'[i] = a[i]
    public static int size() {
        return size;
    }

//    Pred: true
//    Post: size == n && for i = 0..n-1: a'[i] = a[i]
    public static boolean isEmpty() {
        return size == 0;
    }

//    Pred: true
//    Post: a = for i = 0..n-1: a'[i] = null && head = 0 && tail = 0 && size = 0
    public static void clear() {
        while (((tail - head) % elements.length) >= 0) {
            elements[tail--] = null;
        }
        head = 0;
        tail = 0;
        size = 0;
    }

//    Pred: element != null
//    Post: min i : a[i] == element || -1
    public static int indexOf(Object element) {
        int i = 0;
        while (i < size) {
            if (elements[(i + head) % elements.length].equals(element)) {
                return i;
            }
            i++;
        }
        return -1;
    }

//    Pred: element != null
//    Post: max i : a[i] == element || -1
    public static int lastIndexOf(Object element) {
        int i = 0;
        while (i < size) {
            if (elements[(tail - i - 1 + elements.length) % elements.length].equals(element)) {
                return size - i - 1;
            }
            i++;
        }
        return -1;
    }
}
