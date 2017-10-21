package ru.spbau.mit.kirakosian;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringHashMapTest {

    private static StringHashMap[] maps = new StringHashMap[3];

    @Before
    public void setUp() {
        maps[0] = new StringHashMap();
        maps[1] = new StringHashMap((s) -> 1);
        maps[2] = new StringHashMap(String::length);
    }

    @Test
    public void testConstruct() {
        StringHashMap map1 = new StringHashMap();
        StringHashMap map2 = new StringHashMap((s) -> 1);
        StringHashMap map3 = new StringHashMap(String::hashCode);
    }

    @Test
    public void testIsEmptySize() {
        for (StringHashMap map : maps) {
            assertTrue(map.isEmpty());
            assertEquals(0, map.size());

            map.put("hello", "one");
            assertFalse(map.isEmpty());
            assertEquals(1, map.size());

            map.put("hello2", "one");
            assertFalse(map.isEmpty());
            assertEquals(2, map.size());
        }
    }

    @Test
    public void testPutGet() {
        for (StringHashMap map : maps) {
            assertNull(map.get(null));

            assertNull(map.put("key1", "one"));
            assertNull(map.put("key2", "two"));
            assertThrows(IllegalArgumentException.class, () -> map.put(null, "three"));
            assertNull(map.get(null));

            assertEquals(2, map.size());
            assertEquals("one", map.get("key1"));
            assertEquals("two", map.get("key2"));

            assertEquals("one", map.put("key1", "four"));
            assertEquals(2, map.size());
            assertNull(map.get(null));

            assertNull(map.put("key3", "five"));
            assertEquals("four", map.get("key1"));
            assertEquals("five", map.get("key3"));
            assertNull(map.get("key4"));
            assertNull(map.get(null));

            assertEquals("five", map.put("key3", null));
            assertNull(map.get("key3"));

            assertThrows(IllegalArgumentException.class, () -> map.put(null, "hello"));
            assertNull(map.put("key3", null));
            assertNull(map.get(null));
            assertEquals(3, map.size());
        }
    }

    @Test
    public void testContains() {
        for (StringHashMap map : maps) {
            assertFalse(map.contains(null));
            assertFalse(map.contains("key1"));

            map.put("key1", "value1");
            assertThrows(IllegalArgumentException.class, () -> map.put(null, null));
            assertFalse(map.contains(null));
            assertTrue(map.contains("key1"));
            assertFalse(map.contains("key2"));
        }
    }

    @Test
    public void testRemove() {
        for (StringHashMap map : maps) {
            assertNull(map.remove(null));

            map.put("key", "value");
            assertTrue(map.contains("key"));
            assertNull(map.remove(null));
            assertEquals("value", map.remove("key"));
            assertFalse(map.contains("key"));

            map.put("a", "b");
            map.put("c", "d");
            map.put("e", "f");
            assertNull(map.remove(""));
            assertNull(map.remove("key"));
            assertEquals("b", map.remove("a"));
            assertEquals(2, map.size());
            assertNull(map.remove("a"));
            map.put("a", null);
            assertNull(map.remove("a"));
        }
    }

    @Test
    public void clear() {
        for (StringHashMap map : maps) {
            map.clear();
            assertEquals(0, map.size());
            assertTrue(map.isEmpty());

            map.put("one", "two");
            map.put("three", "3");
            map.clear();
            assertEquals(0, map.size());
            assertNull(map.get("one"));
            assertFalse(map.contains("three"));
        }
    }

    @Test
    public void testRebuild() {
        for (StringHashMap map : maps) {
            for (int i = 0; i < 10000; i++) {
                map.put(String.valueOf(i), String.valueOf(i));
            }
            assertEquals(10000, map.size());
        }
    }
}