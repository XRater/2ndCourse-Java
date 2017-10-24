package ru.spbau.mit.kirakosian;

import java.io.*;

/**
 * Trie class implementation. This class stores lowercase latin strings. The class supports
 * add, remove and contain operations. Also it is possible to count number of strings that
 * starts with the given prefix. All operations take O(|string|) time to complete.
 */
public class Trie implements Serializable {

    private static final int ALPHABET_SIZE = 26;
    private Vertex root = new Vertex();

    /**
     * The method returns size of Trie.
     */
    public int size() {
        return root.startsOn;
    }

    /**
     * The method adds String to the Trie structure. All stored string must consist of
     * lowercase latin letters. If it is not, IllegalArgumentException will be thrown.
     * If string is equal to null IllegalArgumentException will be thrown as well.
     * @param element string to add
     * @return true if the string was the new one and false otherwise
     */
    public boolean add(final String element) {
        if (!isLegalString(element)) {
            throw new IllegalArgumentException();
        }
        if (this.contains(element)) {
            return false;
        }

        Vertex curr = root;

        for (final char c : element.toCharArray()) {
            final int index  = getIndex(c);
            if (curr.next[index] == null) {
                curr.next[index] = new Vertex();
            }
            curr.startsOn++;
            curr = curr.next[index];
        }

        curr.isTerminal = true;
        curr.startsOn++;
        return true;
    }

    /**
     * The method checks if string is stored in the Trie.
     * @param element string to find
     */
    public boolean contains(final String element) {
        final Vertex vertex = find(element);
        return vertex != null && vertex.isTerminal;
    }

    /**
     * The method removes given String from the Trie.
     * The method returns false for all types of Illegal Arguments
     * @param element String to remove
     * @return true if the elements was in the Trie and false otherwise.
     */
    public boolean remove(final String element) {
        if (!isLegalString(element)) {
            return false;
        }
        if (!this.contains(element)) {
            return false;
        }

        Vertex curr = root;
        for (final char c : element.toCharArray()) {
            final int index = getIndex(c);
            if (curr.next[index] == null) {
                return false;
            }
            curr.startsOn--;
            curr = curr.next[index];
        }

        curr.isTerminal = false;
        curr.startsOn--;
        return true;
    }

    /**
     * The method returns number of stored strings starting with the given prefix.
     * Takes O(|prefix|) time.
     */
    public int howManyStartsWithPrefix(final String prefix) {
        final Vertex vertex = find(prefix);
        return vertex == null ? 0 : vertex.startsOn;
    }

    /**
     * The method writes the Trie to the given OutputStream as a sequence of bytes.
     * @param out OutputStream to write in
     */
    public void serialize(final OutputStream out) throws IOException {
        final ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(this);
    }

    /**
     * The method reads instance of Trie class from the given InputStream and replaces
     * the actual one with it.
     * @param in InputStream to read from
     */
    public void deserialize(final InputStream in) throws IOException, ClassNotFoundException {
        final ObjectInputStream is = new ObjectInputStream(in);
        final Trie tmp;
        tmp = (Trie) is.readObject();
        root = tmp.root;
    }

    private Vertex find(final String element) {
        if (!isLegalString(element)) {
            return null;
        }

        Vertex curr = root;
        for (final char c : element.toCharArray()) {
            final int index = getIndex(c);
            if (curr.next[index] == null) {
                return null;
            }
            curr = curr.next[index];
        }
        return curr;
    }

    private int getIndex(final char c) {
        return  c - 'a';
    }

    private boolean isLegalString(final String s) {
        if (s == null) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < 'a' || s.charAt(i) > 'z') {
                return false;
            }
        }
        return true;
    }

    static private class Vertex implements Serializable {
        private final Vertex[] next = new Vertex[ALPHABET_SIZE];
        private boolean isTerminal;
        private int startsOn;
    }

}


