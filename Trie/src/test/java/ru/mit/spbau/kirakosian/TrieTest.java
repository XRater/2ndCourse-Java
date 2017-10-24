package ru.mit.spbau.kirakosian;

import org.junit.Test;
import ru.spbau.mit.kirakosian.Trie;

import java.io.*;

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

    @Test
    public void testSerializeDeserializeEmpty() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream os;
        ByteArrayInputStream is;

        Trie trie = new Trie();
        Trie other = new Trie();

        os = new ByteArrayOutputStream();
        other.serialize(os);
        is = new ByteArrayInputStream(os.toByteArray());
        trie.deserialize(is);

        assertEquals(0, trie.size());
        assertTrue(trie.add("hello"));
        assertTrue(trie.contains("hello"));
        assertEquals(1, trie.size());

        other.serialize(os);
        is = new ByteArrayInputStream(os.toByteArray());
        trie.deserialize(is);

        assertEquals(0, trie.size());
        assertFalse(trie.contains("hello"));
        assertTrue(trie.add("hello"));
        assertTrue(trie.contains("hello"));
        assertEquals(1, trie.size());
    }

    @Test
    public void testSerializeDeserializeRegular() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream os;
        ByteArrayInputStream is;

        Trie trie = new Trie();
        Trie other = new Trie();
        other.add("one");
        other.add("");
        other.add("o");
        other.add("two");

        os = new ByteArrayOutputStream();
        other.serialize(os);
        is = new ByteArrayInputStream(os.toByteArray());
        trie.deserialize(is);

        assertEquals(4, trie.size());
        assertTrue(trie.contains("one"));
        assertTrue(trie.contains(""));
        assertTrue(trie.contains("o"));
        assertTrue(trie.contains("two"));
        assertEquals(2, trie.howManyStartsWithPrefix("o"));

        assertTrue(trie.remove("o"));
        assertTrue(trie.remove("two"));
        assertTrue(trie.add("t"));

        other.serialize(os);
        is = new ByteArrayInputStream(os.toByteArray());
        trie.deserialize(is);

        assertEquals(4, trie.size());
        assertTrue(trie.contains("one"));
        assertTrue(trie.contains(""));
        assertTrue(trie.contains("o"));
        assertTrue(trie.contains("two"));
        assertEquals(2, trie.howManyStartsWithPrefix("o"));
        assertFalse(trie.contains("t"));
        assertEquals(1, trie.howManyStartsWithPrefix("t"));
    }

    @Test
    public void testSerializeDeserializeException() throws IOException {
        ByteArrayOutputStream os;
        ByteArrayInputStream is;

        Trie trie = new Trie();
        Trie other = new Trie();

        assertThrows(NullPointerException.class, () -> other.serialize(null));
        os = new ByteArrayOutputStream();
        os.write(10);
        is = new ByteArrayInputStream(os.toByteArray());
        assertThrows(IOException.class , () -> trie.deserialize(is));
    }
}