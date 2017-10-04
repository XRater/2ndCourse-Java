import java.io.*;

/**
 *
 */
public class Trie implements Serializable {

    private static final int ALPHABET_SIZE = 26;
    private Vertex root = new Vertex();
    private int size;

    /**
     * The method returns size of Trie.
     */
    public int size() {
        return size;
    }

    /**
     * The method adds String to the Trie structure. All stored string must consist of
     * lowercase english letters. If it is not, IllegalArgumentException will be thrown.
     * If string is equal to null IllegalArgumentException will be thrown.
     * @param element string to add
     * @return true if the string was the new one and false otherwise
     */
    public boolean add(String element) {
        if (!isLegalString(element)) {
            throw new IllegalArgumentException();
        }
        if (this.contains(element)) {
            return false;
        }

        Vertex curr = root;

        for (char c : element.toCharArray()) {
            int index  = getIndex(c);
            if (curr.next[index] == null) {
                curr.next[index] = new Vertex();
            }
            curr.startsWith++;
            curr = curr.next[index];
        }

        curr.isTerminal = true;
        curr.startsWith++;
        size++;
        return true;
    }

    /**
     * The method checks if string is stored in Trie.
     * @param element string to find
     */
    public boolean contains(String element) {
        Vertex vertex = find(element);
        return vertex != null && vertex.isTerminal;
    }

    /**
     * The method removes given String from Trie.
     * The method returns false for all types of Illegal Arguments
     * @param element String to remove
     * @return true if the elements was in the Trie and false otherwise.
     */
    public boolean remove(String element) {
        if (!isLegalString(element)) {
            return false;
        }
        if (!this.contains(element)) {
            return false;
        }

        Vertex curr = root;
        for (char c : element.toCharArray()) {
            int index = getIndex(c);
            if (curr.next[index] == null) {
                return false;
            }
            curr.startsWith--;
            curr = curr.next[index];
        }

        curr.isTerminal = false;
        curr.startsWith--;
        size--;
        return true;
    }

    /**
     * The method returns number of stored strings starting with the given prefix.
     * Takes O(|prefix|) time.
     */
    public int howManyStartsWithPrefix(String prefix) {
        Vertex vertex = find(prefix);
        return vertex == null ? 0 : vertex.startsWith;
    }

    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(this);
    }

    public void deserialize(InputStream in) throws IOException {
        ObjectInputStream is = new ObjectInputStream(in);
        Trie tmp;
        try {
            tmp = (Trie) is.readObject();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException();
        }
        root = tmp.root;
        size = tmp.size;
    }

    private Vertex find(String element) {
        if (!isLegalString(element)) {
            return null;
        }

        Vertex curr = root;
        for (char c : element.toCharArray()) {
            int index = getIndex(c);
            if (curr.next[index] == null) {
                return null;
            }
            curr = curr.next[index];
        }
        return curr;
    }

    private int getIndex(char c) {
        return  c - 'a';
    }

    private boolean isLegalString(String s) {
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
        private int startsWith;
    }

}


