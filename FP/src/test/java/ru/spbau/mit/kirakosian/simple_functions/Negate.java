package ru.spbau.mit.kirakosian.simple_functions;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kirakosian.Function1;

public class Negate extends Function1<Integer, Integer> {

    @NotNull
    @Override
    public Integer apply(final Integer v) {
        return -v;
    }
}
