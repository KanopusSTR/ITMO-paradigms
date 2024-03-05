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

public interface Queue {
    /*public*/ void enqueue(Object element);
    /*public*/ Object dequeue();
    /*public*/ Object element();
    /*public*/ int size();
    /*public*/ boolean isEmpty();
    /*public*/ int indexOf(Object element);
    /*public*/ int lastIndexOf(Object element);
    /*public*/ void clear();

    int indexIf(Predicate<Object> tester);
    int lastIndexIf(Predicate<Object> tester);
}
