package ru.spbau.mit.kirakosian.simple_functions;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kirakosian.Function2;

public class Sum extends Function2<Integer, Integer, Integer> {

    @NotNull
    @Override
    public Integer apply(final Integer a, final Integer b) {
        return a + b;
    }
}
