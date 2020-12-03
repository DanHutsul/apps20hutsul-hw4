package ua.edu.ucu.immutable;

import java.util.Arrays;

public class ImmutableLinkedList implements ImmutableList {

    // A private class of Node to save time
    private class Node {
        private Object value;
        private Node next = null;

        public Node(Object e) {
            value = e;
        }

        public void setValue(Object e) {
            value = e;
        }

        public void setNext(Node newNext) {
            next = newNext;
        }

        public Object getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public Node copy() {
            Node startNode = new Node(value);
            Node currentNode = this;
            Node newNode = startNode;
            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
                newNode.setNext(new Node(currentNode.getValue()));
                newNode = newNode.getNext();
            }
            return startNode;
        }
    }

    private final Node head;
    private final int listLength;

    public ImmutableLinkedList() {
        head = null;
        listLength = 0;
    }

    private ImmutableLinkedList(Node base, int size) {
        if (base == null) {
            head = null;
        } else {
            head = base.copy();
        }
        listLength = size;
    }

    public ImmutableLinkedList(Object[] e) {
        if (e.length == 0) {
            head = null;
            listLength = 0;
        }
        else {
            listLength = e.length;
            head = new Node(e[0]);
            Node currentNode = head;
            for (int i = 1; i < e.length; i++) {
                Node newNode = new Node(e[i]);
                currentNode.setNext(newNode);
                currentNode = currentNode.getNext();
            }
        }
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(size(), e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size(), c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (isEmpty()) {
            return new ImmutableLinkedList(c);
        }
        if (index == 0) {
            return new ImmutableLinkedList(c).addAll(toArray());
        }

        Node newNode = head.copy();
        Node currentNode = newNode;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.getNext();
        }
        Node indexNode = currentNode.getNext();
        for (int i = 0; i < c.length; i++) {
            currentNode.setNext(new Node(c[i]));
            currentNode = currentNode.getNext();
        }
        currentNode.setNext(indexNode);
        return new ImmutableLinkedList(newNode, size() + c.length);
    }

    @Override
    public Object get(int index) {
        indexCheck(index);
        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode.getValue();
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        indexCheck(index);

        Node newNode = head.copy();
        if (index == 0) {
            newNode = newNode.getNext();
        }
        else {
            Node currentNode = newNode;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(currentNode.getNext().getNext());
        }
        return new ImmutableLinkedList(newNode, size() - 1);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        indexCheck(index);
        Node newNode = head.copy();
        Node currentNode = newNode;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        currentNode.setValue(e);
        return new ImmutableLinkedList(newNode, size());
    }

    @Override
    public int indexOf(Object e) {
        Node currentNode = head;
        for (int i = 0; i < listLength; i++) {
            if (e.equals(currentNode.getValue())) {
                return i;
            }
            currentNode = currentNode.getNext();
        }
        return -1;
    }

    @Override
    public int size() {
        return listLength;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Object[] toArray() {
        Object[] linkedListArray = new Object[listLength];
        Node currentNode = head;
        for (int i = 0; i < listLength; i++) {
            linkedListArray[i] = currentNode.getValue();
            currentNode = currentNode.getNext();
        }
        return linkedListArray;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        sizeCheck();
        return get(0);
    }

    public Object getLast() {
        sizeCheck();
        return get(size() - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(size() - 1);
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void sizeCheck() {
        if (size() == 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}
