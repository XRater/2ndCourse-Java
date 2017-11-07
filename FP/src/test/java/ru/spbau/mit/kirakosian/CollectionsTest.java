package ru.spbau.mit.kirakosian;

import org.junit.Test;
import ru.spbau.mit.kirakosian.simple_functions.Sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionsTest {

    @Test
    public void testFoldLSimple() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals((Integer) 6, Collections.foldl(new Sum(), list, 0));
    }

    @Test
    public void testFoldRSimple() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals((Integer) 6, Collections.foldr(new Sum(), list, 0));
    }

}