package ru.spbau.mit.kirakosian;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeyValueStringListTest {

    @Test
    public void testCreate() throws Exception {
        KeyValueStringList list = new KeyValueStringList();
    }

    @Test
    public void testAddSize() throws Exception {
        KeyValueStringList list = new KeyValueStringList();

        assertEquals(list.size(), 0);

        list.add("one", "hi");
        assertEquals(list.size(), 1);

        list.add("", null);
        assertEquals(list.size(), 2);

        list.add(null, "three");
        assertEquals(list.size(), 2);

        list.add("", null);
        assertEquals(list.size(), 3);
    }

    @Test
    public void testToString() {
        KeyValueStringList list = new KeyValueStringList();

        assertEquals(list.toString(), "{}");

        list.add("hello", "one");
        assertEquals(list.toString(), "{hello: one}");

        list.add("hello", "two");
        assertEquals(list.toString(), "{hello: one, hello: two}");

        list.add("", "");
        assertEquals(list.toString(), "{hello: one, hello: two, : }");

        list.add("null", "three");
        assertEquals(list.toString(), "{hello: one, hello: two, : , null: three}");
    }

    @Test
    public void testRemove() {
        KeyValueStringList list = new KeyValueStringList();
        assertEquals(list.remove("one"), null);
        assertEquals(list.remove(null), null);

        list.add(null, "zero");
        list.add("Hello", "one");
        list.add("Hello", "two");
        list.add("", null);
        list.add("12", "three");

        assertEquals(list.isEmpty(), false);
        assertEquals(list.remove(null), null);
        assertEquals(list.remove(""), null);
        assertEquals(list.remove(""), null);
        assertEquals(list.size(), 3);

        assertEquals(list.remove("Hello"), "one");
        assertEquals(list.size(), 2);

        assertEquals(list.remove("one"), null);
        assertEquals(list.size(), 2);

        assertEquals(list.remove("12"), "three");
        assertEquals(list.size(), 1);

        assertEquals(list.remove("Hello"), "two");
        assertEquals(list.size(), 0);
        assertEquals(list.isEmpty(), true);
    }

    @Test
    public void clear() {
        KeyValueStringList list = new KeyValueStringList();
        list.add("one", "one");
        list.add(null, "two");
        list.add(null, null);
        list.clear();

        assertEquals(list.isEmpty(), true);
        assertEquals(list.size(), 0);
    }
}