public class Trie {

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
            curr = curr.next[index];
        }

        curr.isTerminal = true;
        size++;
        return true;
    }

    /**
     * The method checks if string is stored in Trie.
     * @param element string to find
     */
    public boolean contains(String element) {
        if (!isLegalString(element))
            return false;

        Vertex curr = root;
        for (char c : element.toCharArray()) {
            int index = getIndex(c);
            if (curr.next[index] == null)
                return false;
            curr = curr.next[index];
        }

        return curr.isTerminal;
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

    static private class Vertex {
        private final Vertex[] next = new Vertex[ALPHABET_SIZE];
        private boolean isTerminal;
    }

}


