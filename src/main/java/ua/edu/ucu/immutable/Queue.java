package ua.edu.ucu.immutable;

public class Queue {
    private ImmutableLinkedList queue;

    public Queue() {
        queue = new ImmutableLinkedList();
    }

    public ImmutableLinkedList getQueue()
    {
        return queue;
    }

    public Object peek() {
        if (queue.size() == 0) {
            throw new IndexOutOfBoundsException();
        }
        return queue.getFirst();
    }

    public Object dequeue() {
        Object element = peek();
        queue = queue.removeFirst();
        return element;
    }

    public void enqueue(Object e) {
        queue = queue.addLast(e);
    }

    public int size() {
        return this.queue.size();
    }
}
