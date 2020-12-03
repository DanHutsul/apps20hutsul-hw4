package ua.edu.ucu.tries;

import ua.edu.ucu.immutable.Queue;

import java.util.ArrayList;

public class RWayTrie implements Trie {

    private static final int R = 26;
    private Node head = new Node(null);
    private int size = 0;

    // A private class of Node
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


    @Override
    public void add(Tuple t) {
        String word = t.term;
        Node currentNode = head;
        if (size() == 0) {
            head.setValue(word);
        }

        else {
            for (int x = 0; x < size - 1; x++) {
                currentNode = head.getNext();
            }
            currentNode.setNext(new Node(word));
        }
        size++;
    }

    @Override
    public boolean contains(String word) {
        Node currentNode = head;
        for (int x = 0; x < size - 1; x++) {
            if (currentNode.getValue().toString().contains(word)) {
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }

    @Override
    public boolean delete(String word) {
        if (!contains(word) || size == 0) {
            return false;
        }
        int x = 0;
        Node previousNode = head;
        Node currentNode = head;
        while (x < size) {
            previousNode = currentNode.copy();
            currentNode = currentNode.getNext();
            if (currentNode.getValue().toString().contains(word)) {
                previousNode.setNext(currentNode.getNext());
            }
        }
        size--;
        return true;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(head.copy(), s, q);

        ArrayList<String> result = new ArrayList<>();
        while (!q.getQueue().isEmpty()) {
            result.add((String) q.dequeue());
        }
        return result;
    }

    private void collect(Node x, String pre, Queue q) {
        if (x == null) {
            return;
        }
        if (x.getValue() != null) {
            q.enqueue(pre);
        }
        for (int c = 0; c < R; c++) {
            char symbol = (char) ((int) 'a' + c);
            collect(x.getNext(), pre + symbol, q);
        }
    }

    @Override
    public int size() {
        return this.size;
    }

}
