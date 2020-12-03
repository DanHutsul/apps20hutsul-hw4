package ua.edu.ucu.tries;

import java.util.LinkedList;
import java.util.Queue;

public class RWayTrie implements Trie {
    private static final int R = 256;

    private Node head = new Node();
    private int size;

    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    @Override
    public void add(Tuple t) {
        String word = t.term;
        int value = t.weight;
        if (word == null) {
            throw new IllegalArgumentException("NULL is not a valid value");
        }
        else {
            head = add(head, word, value, 0);
        }
    }

    private Node add(Node node, String word, int value, int length) {
        if (node == null) {
            node = new Node();
        }
        if (length == word.length()) {
            if (node.value == null) {
                this.size++;
            }
            node.value = value;
            return node;
        }
        char c = word.charAt(length);
        node.next[c] = add(node.next[c], word, value, length + 1);
        return node;
    }

    public String get(String word) {
        if (word == null) {
            throw new IllegalArgumentException("NULL is not a valid argument");
        }
        Node node = get(head, word, 0);
        if (node == null) {
            return null;
        }
        return (String) node.value;
    }

    private Node get(Node node, String word, int length) {
        if (node == null) {
            return null;
        }
        if (length == word.length()) {
            return node;
        }
        return get(node.next[word.charAt(length)], word, length + 1);
    }

    @Override
    public boolean contains(String word) {
        if (word == null) {
            throw new IllegalArgumentException("NULL is not a valid argument");
        }
        return get(word) != null;
    }

    @Override
    public boolean delete(String word) {
        if (word == null) {
            throw new IllegalArgumentException("NULL is not a valid argument");
        }
        head = delete(head, word, 0);
        return head != null;
    }

    private Node delete(Node node, String word, int length) {
        if (node == null) {
            return null;
        }
        if (length == word.length()) {
            if (node.value != null) {
                size--;
            }
            node.value = null;
        }
        else {
            char c = word.charAt(length);
            node.next[c] = delete(node.next[c], word, length + 1);
        }
        if (node.value != null) {
            return node;
        }
        for (int c = 0; c < R; c++) {
            if (node.next[c] != null) {
                return node;
            }
        }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue<String> result = new LinkedList<>();
        Node node = get(head, s, 0);
        collect(node, new StringBuilder(s), result);
        return result;
    }

    private void collect(Node node, StringBuilder s, Queue<String> result) {
        if (node == null) {
            return;
        }
        if (node.value != null) {
            result.offer(s.toString());
        }
        for (char c = 0; c < R; c++) {
            s.append(c);
            collect(node.next[c], s, result);
            s.deleteCharAt(s.length() - 1);
        }
    }

    @Override
    public int size() {
        return this.size;
    }

}