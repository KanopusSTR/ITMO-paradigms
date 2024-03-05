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

public class LinkedQueue extends AbstractQueue {
    private Node tail;
    private Node head;

//    Pred: element != null && queue != null
//    Post: n' = n + 1 && a[n] = element
    protected void enqueueImpl(Object element) {
        if (size == 0) {
            head = tail = new Node(element, tail, tail);
        } else {
            tail = tail.last = new Node(element, tail, tail);
        }
    }

//    Pred: n >= 1 && queue != null
//    Post: n' = n && for i = 1..n'-1 : a'[i] = a[i] && a'[0] = null
    protected void remove() {
        head = head.last;
    }

//    Pred: n >= 1 && queue != null
//    Post: R = a[0] && n' = n && for i = 0..n-1: a'[i] = a[i]
    protected Object elementImpl() {
        return head.value;
    }

    private static class Node {
        private final Object value;
        private final Node next;
        private Node last;

        public Node(Object value, Node next, Node last) {
            assert value != null;

            this.value = value;
            this.next = next;
            this.last = last;
        }
    }
}
