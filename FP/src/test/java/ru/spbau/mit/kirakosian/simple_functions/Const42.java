package ru.spbau.mit.kirakosian.simple_functions;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kirakosian.Function1;

public class Const42<V> extends Function1<V, Integer> {

    @NotNull
    @Override
    public Integer apply(final V v) {
        return 42;
    }
}
