package queue;

/*
Model: a[0]..a[n-1]
Invariant: for i = 0..n-1: a[i] != null

Pred: element != null && queue != null
Post: n' = n + 1 && a[n] = element
    enqueue(queue, element)

Pred: n >= 1 && queue != null
Post: n' = n - 1 && for i = 0..n'-1 : a'[i] = a[i + 1]
    dequeue(queue)

Pred: n >= 1 && queue != null
Post: R = a[0] && n' = n && for i = 0..n-1: a'[i] = a[i]
    element(queue)

Pred: queue != null
Post: a = for i = 0..n-1: a'[i] = null && n' = 0
    clear(queue)

Pred: true
Post: R = n
    size(queue)

Pred: true
Post: n == 0
    isEmpty(queue)

Pred: element != null && queue != null
Post: min i : a[i] == element || -1
indexOf(queue, element)

Pred: element != null && queue != null
Post: max i : a[i] == element || -1
lastIndexOf(queue, element)

Pred: queue != null && tester != 0
Post: min i : a[i] удовлетворяет tester || -1
indexIf(tester)

Pred: queue != null && tester != 0
Post: max i : a[i] удовлетворяет tester || -1
lastIndexIf(tester)
*/

import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int size;

//    Pred: element != null && queue != null
//    Post: n' = n + 1 && a[n] = element
    public void enqueue(Object element) {
        assert element != null;

        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

//    Pred: n >= 1 && queue != null
//    Post: R = a[0] && n' = n && for i = 0..n-1: a'[i] = a[i]
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    //   Pred: n >= 1 && queue != null
    //   Post: n' = n - 1 && for i = 0..n'-1 : a'[i] = a[i + 1]
    public Object dequeue() {
        assert size > 0;

        Object result = element();
        remove();
        size--;
        return result;
    }

    protected abstract void remove();

    //    Pred: queue != null
    //    Post: R = n && for i = 0..n-1: a'[i] = a[i]
    public int size() {
        return size;
    }

    //    Pred: queue != null
    //    Post: size == n && for i = 0..n-1: a'[i] = a[i]
    public boolean isEmpty() {
        return size == 0;
    }

//    Pred: queue != null
//    Post: a = for i = 0..n-1: a'[i] = null && n' = 0
    public void clear() {
        while (size > 0) {
            remove();
            size--;
        }
    }

    //    Pred: element != null && queue != null
    //    min i : a[i] == element || -1
    public int indexOf(Object element) {
        int answer = -1;
        for (int i = 0; i < size; i++) {
            if (answer == -1 && element().equals(element)) {
                answer = i;
            }
            Object x = dequeue();
            enqueue(x);
        }
        return answer;
    }

    //    Pred: element != null && queue != null
    //    max i : a[i] == element || -1
    public int lastIndexOf(Object element) {
        int answer = -1;
        for (int i = 0; i < size; i++) {
            if (element().equals(element)) {
                answer = i;
            }
            Object x = dequeue();
            enqueue(x);
        }
        return answer;
    }

    //    Pred: queue != null && tester != 0
    //   min i : a[i] удовлетворяет tester || -1
    public int indexIf(Predicate<Object> tester) {
        int answer = -1;
        for (int i = 0; i < size; i++) {
            if (answer == -1 && tester.test(element())) {
                answer = i;
            }
            Object x = dequeue();
            enqueue(x);
        }
        return answer;
    }

    //    Pred: queue != null && tester != 0
    //   max i : a[i] удовлетворяет tester || -1
    public int lastIndexIf(Predicate<Object> tester) {
        int answer = -1;
        for (int i = 0; i < size; i++) {
            if (tester.test(element())) {
                answer = i;
            }
            Object x = dequeue();
            enqueue(x);
        }
        return answer;
    }
}
