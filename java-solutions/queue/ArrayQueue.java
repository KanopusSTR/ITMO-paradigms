package queue;

/*
Model: a[0]..a[n-1]
Invariant: for i = 0..n-1: a[i] != null

Pred: element != null && queue != null
Post: n' = n + 1 && a[n] = element
    enqueueImpl(queue, element)

Pred: n >= 1 && queue != null
Post: R = a[0] && n' = n && for i = 0..n-1: a'[i] = a[i]
    elementImpl(queue)

Pred: n >= 1 && queue != null
Post: n' = n && for i = 1..n'-1 : a'[i] = a[i] && a'[0] = null
remove(queue)
*/

public class ArrayQueue extends AbstractQueue {
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[5];

//    Pred: element != null && queue != null
//    Post: n' = n + 1 && a[n] = element
        protected void enqueueImpl(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    private void ensureCapacity(int capacity) {
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
//    Post: R = a[0] && n' = n && for i = 0..n'-1: a'[i] = a[i]
    protected Object elementImpl() {
        assert size > 0;
        return elements[head];
    }

    //    Pred: n >= 1 && queue != null
    //    Post: n' = n && for i = 1..n'-1 : a'[i] = a[i] && a'[0] = null
    protected void remove() {
        assert size > 0;
        Object x = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
    }
}
