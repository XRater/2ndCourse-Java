package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.*;

public class SetTest {

    @Test
    public void testSizeOrdered() {
        checkSizeSet(new Integer[]{});
        checkSizeSet(new Integer[]{1, 2, 3, 4, 5, 6});
        checkSizeSet(new Integer[]{-1, -2, -3, -4, -5, -6});
        checkSizeSet(new Integer[]{6, 3, 5, 2, 4, 1, 8});

        checkSizeSet(new String[]{"a", "sd", "second", "aa", "hello"});
    }

    @Test
    public void testAddContains() {
        checkAddSet(new Integer[]{});
        checkAddSet(new Integer[]{1, 2, 3, 4, 5, 6});
        checkAddSet(new Integer[]{-1, -2, -3, -4, -5, -6});
        checkAddSet(new Integer[]{6, 3, 5, 2, 4, 1, 8});

        checkAddSet(new String[]{"a", "sd", "string", "aa", "hello"});
    }

    private <T extends Comparable<T>> void checkAddSet(@NotNull final T[] data) {
        final Set<T> set = new Set<>();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < i; j++) {
                assertTrue(set.contains(data[j]));
            }
            for (int j = i; j < data.length; j++) {
                assertFalse(set.contains(data[j]));
            }
            set.add(data[i]);
            for (int j = 0; j < i + 1; j++) {
                assertTrue(set.contains(data[j]));
            }
            for (int j = i + 1; j < data.length; j++) {
                assertFalse(set.contains(data[j]));
            }
            set.add(data[i]);
            for (int j = 0; j < i + 1; j++) {
                assertTrue(set.contains(data[j]));
            }
            for (int j = i + 1; j < data.length; j++) {
                assertFalse(set.contains(data[j]));
            }
        }
    }

    private <T extends Comparable<T>> void checkSizeSet(@NotNull final T[] data) {
        final Set<T> set = new Set<>();
        assertTrue(set.isEmpty());
        assertEquals(0, 0);
        for (int i = 0; i < data.length; i++) {
            assertTrue(set.add(data[i]));
            assertEquals(i + 1, set.size());
            assertFalse(set.isEmpty());
        }

        for (final T element : data) {
            assertFalse(set.add(element));
            assertEquals(data.length, set.size());
            assertFalse(set.isEmpty());
        }
    }
}