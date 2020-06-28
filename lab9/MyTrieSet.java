import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyTrieSet implements TrieSet61B {
    private Node root;

    private class Node {
        private boolean isKey;
        private Map<Character, Node> children; //when there are lots of children, use a map, this is equal to p.left, p,right...

        private Node(boolean isKey) {
            this.isKey = isKey;
            children = new HashMap<>();
        }
    }

    public MyTrieSet() {
        this.clear();
    }

    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = new Node(false);
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) return false;
        Node p = root;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (!p.children.containsKey(ch)) return false;
            p = p.children.get(ch);
        }
        //matching all chars, still need to be isKey
        return p.isKey;
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) return;
        Node p = root;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (!p.children.containsKey(ch)) {
                p.children.put(ch, new Node(false));
            }
            p = p.children.get(ch); //when there are lots of children, use a map, this is equal to p.left, p,right...
        }
        p.isKey = true;
    }


    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> keys = new LinkedList<>();
        if (prefix == null || prefix.length() < 1) return keys;

        //find prefix, similar to contain
        Node p = root;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            if (!p.children.containsKey(ch)) return keys; //doesn't contain prefix, return empty list
            p = p.children.get(ch);
        }

        //now node p points at the last char of prefix, then add all words that starts with prefix
        for (char ch: p.children.keySet()) {
            keys = collect(prefix + ch, p.children.get(ch), keys);
        }
        return keys;
    }

    /** Returns a list of all the words after Node p in the trie, doesn't include p */
    private List<String> collect(Node p) {
        List<String> keysList = new LinkedList<>();
        for (char ch: p.children.keySet()) {
            keysList = collect(ch + "", p.children.get(ch), keysList);
        }
        return keysList;
    }

    /** Returns a list of all the words after Node p with PREFIX str, doesn't include p */
    private List<String> collect(String str, Node p, List<String> keysList) {
        if (p.isKey) keysList.add(str);
        for (char ch: p.children.keySet()) {
            keysList = collect(str + ch, p.children.get(ch), keysList);
        }
        return keysList;
    }


    @Override
    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }


}
