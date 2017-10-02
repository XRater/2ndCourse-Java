import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Trie.
 */
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

    @Test
    public void testSize() {
        Trie trie = new Trie();
        assertEquals(0, trie.size());

        trie.add("one");
        assertEquals(1, trie.size());

        trie.add("one");
        assertEquals(1, trie.size());

        trie.add("");
        assertEquals(2, trie.size());

        trie.add("one");
        assertEquals(2, trie.size());

        assertThrows(IllegalArgumentException.class, () -> trie.add(null));
        assertEquals(2, trie.size());

        trie.add("two");
        assertEquals(3, trie.size());
    }

    @Test
    public void testRemove() {
        Trie trie = new Trie();
        assertFalse(trie.remove(null));
        assertFalse(trie.remove(""));
        assertFalse(trie.remove("asd"));
        assertEquals(0, trie.size());

        trie.add("one");
        trie.add("o");
        trie.add("two");
        trie.add("");
        trie.add("two");
        assertEquals(4, trie.size());
        assertTrue(trie.remove("o"));
        assertEquals(3, trie.size());

        assertFalse(trie.remove("o"));
        assertFalse(trie.remove("on"));
        assertFalse(trie.remove("tw"));

        assertTrue(trie.remove("one"));
        assertEquals(2, trie.size());

        assertTrue(trie.remove("two"));
        assertFalse(trie.remove("two"));
        assertTrue(trie.remove(""));

        assertEquals(0, trie.size());
    }

    @Test
    public void testHowManyStartsWithPrefix() {
        Trie trie = new Trie();
        assertEquals(0, trie.howManyStartsWithPrefix(null));
        assertEquals(0, trie.howManyStartsWithPrefix(""));
        assertEquals(0, trie.howManyStartsWithPrefix("a"));
        assertEquals(0, trie.howManyStartsWithPrefix("Asf"));

        trie.add("one");
        trie.add("two");
        trie.add("one");
        trie.add("");
        trie.add("o");

        assertEquals(4, trie.howManyStartsWithPrefix(""));
        assertEquals(2, trie.howManyStartsWithPrefix("o"));
        assertEquals(1, trie.howManyStartsWithPrefix("one"));
        assertEquals(1, trie.howManyStartsWithPrefix("two"));
        assertEquals(1, trie.howManyStartsWithPrefix("on"));
        assertEquals(1, trie.howManyStartsWithPrefix("t"));
        assertEquals(1, trie.howManyStartsWithPrefix("tw"));
        assertEquals(0, trie.howManyStartsWithPrefix("onee"));

        trie.remove("o");
        assertEquals(3, trie.howManyStartsWithPrefix(""));
        assertEquals(1, trie.howManyStartsWithPrefix("o"));
        assertEquals(1, trie.howManyStartsWithPrefix("one"));
        assertEquals(1, trie.howManyStartsWithPrefix("two"));
        assertEquals(1, trie.howManyStartsWithPrefix("on"));
        assertEquals(1, trie.howManyStartsWithPrefix("t"));
        assertEquals(1, trie.howManyStartsWithPrefix("tw"));
        assertEquals(0, trie.howManyStartsWithPrefix("onee"));

        trie.remove("one");
        assertEquals(2, trie.howManyStartsWithPrefix(""));
        assertEquals(0, trie.howManyStartsWithPrefix("o"));
        assertEquals(0, trie.howManyStartsWithPrefix("one"));
        assertEquals(1, trie.howManyStartsWithPrefix("two"));
        assertEquals(0, trie.howManyStartsWithPrefix("on"));
        assertEquals(1, trie.howManyStartsWithPrefix("t"));
        assertEquals(1, trie.howManyStartsWithPrefix("tw"));
        assertEquals(0, trie.howManyStartsWithPrefix("onee"));
    }
}