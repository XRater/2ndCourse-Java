package java.ru.spbau.mit.kirakosian;

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
    public void testGet() {
        KeyValueStringList list = new KeyValueStringList();
        assertEquals(list.get(null), null);
        assertEquals(list.get(""), null);
        assertEquals(list.get("hello"), null);

        list.add(null, "one");
        list.add("hello", "two");
        list.add("hello", "three");
        list.add("hello2", "four");
        assertEquals(list.get(null), null);
        assertEquals(list.get("hello"), "two");
        assertEquals(list.get("hello2"), "four");
        assertEquals(list.get("one"), null);

        list.remove("hello");
        list.remove("hello2");
        assertEquals(list.get("hello"), "three");
        assertEquals(list.get("hello2"), null);
    }

    @Test
    public void testReplace() {
        KeyValueStringList list = new KeyValueStringList();
        assertEquals(list.replace(null, "one"), null);
        assertEquals(list.isEmpty(), true);

        assertEquals(list.replace("hello", "two"), null);
        assertEquals(list.size(), 1);
        assertEquals(list.toString(), "{hello: two}");

        assertEquals(list.replace("hello", "one"), "two");
        assertEquals(list.size(), 1);
        assertEquals(list.toString(), "{hello: one}");

        list.add("hello", "three");
        assertEquals(list.size(), 2);
        assertEquals(list.toString(), "{hello: one, hello: three}");

        assertEquals(list.replace(null, "four"), null);
        assertEquals(list.replace("hello", "five"), "one");
        assertEquals(list.toString(), "{hello: five, hello: three}");

        assertEquals(list.replace("hello2", "six"), null);
        assertEquals(list.toString(), "{hello: five, hello: three, hello2: six}");
    }

    @Test
    public void testContains() {
        KeyValueStringList list = new KeyValueStringList();
        assertEquals(list.contains(null), false);
        assertEquals(list.contains(""), false);
        assertEquals(list.contains("one"), false);

        list.add("key1", "one");
        list.add("key2", "one");
        list.add("key1", "three");
        assertEquals(list.contains(null), false);
        assertEquals(list.contains("key1"), true);
        assertEquals(list.contains("key2"), true);

        list.replace("key1", null);
        list.remove("key1");
        assertEquals(list.contains("key1"), true);
        assertEquals(list.contains("key2"), true);

        list.remove("key1");
        assertEquals(list.contains("key2"), true);
        assertEquals(list.contains("key1"), false);

        list.clear();
        assertEquals(list.contains("key2"), false);
        assertEquals(list.contains("key1"), false);
    }

    @Test
    public void testClear() {
        KeyValueStringList list = new KeyValueStringList();
        list.add("one", "one");
        list.add(null, "two");
        list.add(null, null);
        list.clear();

        assertEquals(list.isEmpty(), true);
        assertEquals(list.size(), 0);
    }

    @Test
    public void testFront() {
        KeyValueStringList list = new KeyValueStringList();

        assertEquals(list.frontKey(), null);
        assertEquals(list.frontValue(), null);

        list.add("key1", "value1");
        list.add("key1", null);
        list.add("key2", "value3");
        assertEquals(list.frontKey(), "key1");
        assertEquals(list.frontValue(), "value1");

        list.remove(list.frontKey());
        assertEquals(list.frontKey(), "key1");
        assertEquals(list.frontValue(), null);

        list.clear();
        assertEquals(list.frontKey(), null);
        assertEquals(list.frontValue(), null);
    }
}