import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {

    @Test
    public void testAddExceptions() {
        Trie trie = new Trie();
        assertThrows(IllegalArgumentException.class,
                () -> trie.add(null));
        assertThrows(IllegalArgumentException.class,
                () -> trie.add("Aaa"));
        assertThrows(IllegalArgumentException.class,
                () -> trie.add("sdgыas"));
        assertThrows(IllegalArgumentException.class,
                () -> trie.add("one_two"));
        assertThrows(IllegalArgumentException.class,
                () -> trie.add("hello^"));
        assertThrows(IllegalArgumentException.class,
                () -> trie.add("badString"));
        assertThrows(IllegalArgumentException.class,
                () -> trie.add("one more"));
        assertThrows(IllegalArgumentException.class,
                () -> trie.add("привеь"));
        assertThrows(IllegalArgumentException.class,
                () -> trie.add("!ска"));
    }

    @Test
    public void testAdd() {
        Trie trie = new Trie();
        assertTrue(trie.add("one"));
        assertTrue(trie.add("on"));
        assertTrue(trie.add("o"));
        assertTrue(trie.add(""));

        assertFalse(trie.add(""));
        assertFalse(trie.add("o"));
        assertFalse(trie.add("on"));
        assertFalse(trie.add("one"));

        assertTrue(trie.add("two"));
        assertTrue(trie.add("ooe"));
        assertTrue(trie.add("tne"));
        assertTrue(trie.add("t"));
        assertTrue(trie.add("oneone"));
        assertTrue(trie.add("onetwo"));
        assertTrue(trie.add("twoone"));
        assertTrue(trie.add("tt"));
        assertTrue(trie.add("ot"));
    }

    @Test
    public void testContains() {
        Trie trie = new Trie();
        assertFalse(trie.contains(null));
        assertFalse(trie.contains("one"));
        assertFalse(trie.contains(""));
        assertFalse(trie.contains("привет"));
        assertFalse(trie.contains("hi друг"));
        assertFalse(trie.contains("AУ"));

        trie.add("one");
        trie.add("o");
        trie.add("three");
        assertFalse(trie.contains("on"));
        assertFalse(trie.contains(""));
        assertFalse(trie.contains("tne"));
        assertFalse(trie.contains("oneee"));
        assertFalse(trie.contains("t"));
        assertFalse(trie.contains("oneone"));
        assertTrue(trie.contains("one"));
        assertTrue(trie.contains("o"));
        assertTrue(trie.contains("three"));

        trie.add("oneone");
        trie.add("");
        assertTrue(trie.contains(""));
        assertTrue(trie.contains("one"));
        assertTrue(trie.contains("oneone"));
        assertTrue(trie.contains("o"));
        assertTrue(trie.contains("three"));

    }
}