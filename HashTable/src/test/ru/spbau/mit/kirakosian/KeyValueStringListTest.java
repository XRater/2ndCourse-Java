package ru.spbau.mit.kirakosian;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("WeakerAccess")
public class KeyValueStringListTest {

    @Test
    public void testCreate() {
        KeyValueStringList list = new KeyValueStringList();
    }

    @Test
    public void testAddSize() {
        KeyValueStringList list = new KeyValueStringList();

        assertEquals(0, list.size());

        list.add("one", "hi");
        assertEquals(1, list.size());

        list.add("", null);
        assertEquals(2, list.size());

        assertThrows(IllegalArgumentException.class, () -> list.add(null, "three"));
        assertEquals(2, list.size());

        list.add("", null);
        assertEquals(3, list.size());
    }

    @Test
    public void testToString() {
        KeyValueStringList list = new KeyValueStringList();

        assertEquals("{}", list.toString());

        list.add("hello", "one");
        assertEquals("{hello: one}", list.toString());

        list.add("hello", "two");
        assertEquals("{hello: one, hello: two}", list.toString());

        list.add("", "");
        assertEquals("{hello: one, hello: two, : }", list.toString());

        list.add("null", "three");
        assertEquals("{hello: one, hello: two, : , null: three}", list.toString());
    }

    @Test
    public void testRemove() {
        KeyValueStringList list = new KeyValueStringList();
        assertNull(list.remove("one"));
        assertNull(list.remove(null));

        assertThrows(IllegalArgumentException.class, () -> list.add(null, "zero"));
        list.add("Hello", "one");
        list.add("Hello", "two");
        list.add("", null);
        list.add("12", "three");

        assertFalse(list.isEmpty());
        assertNull(list.remove(null));
        assertNull(list.remove(""));
        assertNull(list.remove(""));
        assertEquals(3, list.size());

        assertEquals("one", list.remove("Hello"));
        assertEquals(2, list.size());

        assertNull(list.remove("one"));
        assertEquals(2, list.size());

        assertEquals("three", list.remove("12"));
        assertEquals(1, list.size());

        assertEquals("two", list.remove("Hello"));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGet() {
        KeyValueStringList list = new KeyValueStringList();
        assertNull(list.get(null));
        assertNull(list.get(""));
        assertNull(list.get("hello"));

        assertThrows(IllegalArgumentException.class, () -> list.add(null, "one"));
        list.add("hello", "two");
        list.add("hello", "three");
        list.add("hello2", "four");
        assertNull(list.get(null));
        assertEquals("two", list.get("hello"));
        assertEquals("four", list.get("hello2"));
        assertNull(list.get("one"));

        list.remove("hello");
        list.remove("hello2");
        assertEquals("three", list.get("hello"));
        assertNull(list.get("hello2"));
    }

    @Test
    public void testReplace() {
        KeyValueStringList list = new KeyValueStringList();
        assertNull(list.replace(null, "one"));
        assertTrue(list.isEmpty());

        assertNull(list.replace("hello", "two"));
        assertEquals(1, list.size());
        assertEquals("{hello: two}", list.toString());

        assertEquals("two", list.replace("hello", "one"));
        assertEquals(1, list.size());
        assertEquals("{hello: one}", list.toString());

        list.add("hello", "three");
        assertEquals(2, list.size());
        assertEquals("{hello: one, hello: three}", list.toString());

        assertNull(list.replace(null, "four"));
        assertEquals("one", list.replace("hello", "five"));
        assertEquals("{hello: five, hello: three}", list.toString());

        assertNull(list.replace("hello2", "six"));
        assertEquals("{hello: five, hello: three, hello2: six}", list.toString());
    }

    @Test
    public void testContains() {
        KeyValueStringList list = new KeyValueStringList();
        assertFalse(list.contains(null));
        assertEquals(list.contains(""), false);
        assertEquals(list.contains("one"), false);

        list.add("key1", "one");
        list.add("key2", "one");
        list.add("key1", "three");
        assertFalse(list.contains(null));
        assertTrue(list.contains("key1"));
        assertTrue(list.contains("key2"));

        list.replace("key1", null);
        list.remove("key1");
        assertTrue(list.contains("key1"));
        assertTrue(list.contains("key2"));

        list.remove("key1");
        assertTrue(list.contains("key2"));
        assertFalse(list.contains("key1"));

        list.clear();
        assertFalse(list.contains("key2"));
        assertFalse(list.contains("key1"));
    }

    @Test
    public void testClear() {
        KeyValueStringList list = new KeyValueStringList();
        list.add("one", "one");
        assertThrows(IllegalArgumentException.class, () -> list.add(null, "two"));
        assertThrows(IllegalArgumentException.class, () -> list.add(null, null));
        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testFront() {
        KeyValueStringList list = new KeyValueStringList();

        assertNull(list.frontKey());
        assertNull(list.frontValue());

        list.add("key1", "value1");
        list.add("key1", null);
        list.add("key2", "value3");
        assertEquals("key1", list.frontKey());
        assertEquals("value1", list.frontValue());

        list.remove(list.frontKey());
        assertEquals("key1", list.frontKey());
        assertNull(list.frontValue());

        list.clear();
        assertNull(list.frontKey());
        assertNull(list.frontValue());
    }
}