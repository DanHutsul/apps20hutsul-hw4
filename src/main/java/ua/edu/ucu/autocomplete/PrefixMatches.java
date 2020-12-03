package ua.edu.ucu.autocomplete;
import ua.edu.ucu.immutable.Queue;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (String str : strings) {
            String[] words = str.split(" ");

            for (String word : words) {
                if (word.length() > 2 && !trie.contains(word)) {
                    trie.add(new Tuple(word, word.length()));
                }
            }
        }
        return size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < 2) {
            throw new IllegalArgumentException("String too short");
        }
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        Iterable<String> allWords = wordsWithPrefix(pref);
        Queue q = new Queue();
        ArrayList<String> result = new ArrayList<>();
        if (k > 0) {
            int len = pref.length() + k;
            if (pref.length() == 2) {
                len++;
            }
            for (String str : allWords) {
                if (str.length() < len) {
                    q.enqueue(str);
                }
            }

            while (!q.getQueue().isEmpty()) {
                String x = (String) q.dequeue();
                result.add(x);
            }
        }
        return result;
    }

    public int size() {
        return trie.size();
    }
}
