package ru.spbau.mit.kirakosian;

import org.junit.Before;
import org.junit.Test;
import sun.security.provider.SHA;

import java.util.Random;
import java.util.function.Function;

import static org.junit.Assert.*;

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
        StringHashMap map3 = new StringHashMap(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.hashCode();
            }
        });
    }

    @Test
    public void testIsEmptySize() {
        for (StringHashMap map : maps) {
            assertEquals(map.isEmpty(), true);
            assertEquals(map.size(), 0);

            map.put("hello", "one");
            assertEquals(map.isEmpty(), false);
            assertEquals(map.size(), 1);

            map.put("hello2", "one");
            assertEquals(map.isEmpty(), false);
            assertEquals(map.size(), 2);
        }
    }

    @Test
    public void testPutGet() {
        for (StringHashMap map : maps) {
            assertEquals(map.get(null), null);

            assertEquals(map.put("key1", "one"), null);
            assertEquals(map.put("key2", "two"), null);
            assertEquals(map.put(null, "three"), null);
            assertEquals(map.get(null), null);

            assertEquals(map.size(), 2);
            assertEquals(map.get("key1"), "one");
            assertEquals(map.get("key2"), "two");

            assertEquals(map.put("key1", "four"), "one");
            assertEquals(map.size(), 2);
            assertEquals(map.get(null), null);

            assertEquals(map.put("key3", "five"), null);
            assertEquals(map.get("key1"), "four");
            assertEquals(map.get("key3"), "five");
            assertEquals(map.get("key4"), null);
            assertEquals(map.get(null), null);

            assertEquals(map.put("key3", null), "five");
            assertEquals(map.get("key3"), null);

            assertEquals(map.put(null, "hello"), null);
            assertEquals(map.put("key3", null), null);
            assertEquals(map.get(null), null);
            assertEquals(map.size(), 3);
        }
    }

    @Test
    public void testContains() {
        for (StringHashMap map : maps) {
            assertEquals(map.contains(null), false);
            assertEquals(map.contains("key1"), false);

            map.put("key1", "value1");
            map.put(null, null);
            assertEquals(map.contains(null), false);
            assertEquals(map.contains("key1"), true);
            assertEquals(map.contains("key2"), false);
        }
    }

    @Test
    public void testRemove() {
        for (StringHashMap map : maps) {
            assertEquals(map.remove(null), null);

            map.put("key", "value");
            assertEquals(map.contains("key"), true);
            assertEquals(map.remove(null), null);
            assertEquals(map.remove("key"), "value");
            assertEquals(map.contains("key"), false);

            map.put("a", "b");
            map.put("c", "d");
            map.put("e", "f");
            assertEquals(map.remove(""), null);
            assertEquals(map.remove("key"), null);
            assertEquals(map.remove("a"), "b");
            assertEquals(map.size(), 2);
            assertEquals(map.remove("a"), null);
            map.put("a", null);
            assertEquals(map.remove("a"), null);
        }
    }

    @Test
    public void clear() {
        for (StringHashMap map : maps) {
            map.clear();
            assertEquals(map.size(), 0);
            assertEquals(map.isEmpty(), true);

            map.put("one", "two");
            map.put("three", "3");
            map.clear();
            assertEquals(map.size(), 0);
            assertEquals(map.get("one"), null);
            assertEquals(map.contains("three"), false);
        }
    }

    @Test
    public void testRebuild() {
        for (StringHashMap map : maps) {
            for (int i = 0; i < 10000; i++) {
                map.put(String.valueOf(i), String.valueOf(i));
            }
            assertEquals(map.size(), 10000);
        }
    }
}