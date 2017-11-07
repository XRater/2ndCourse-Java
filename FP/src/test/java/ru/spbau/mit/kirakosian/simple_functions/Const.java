package ru.spbau.mit.kirakosian.simple_functions;

import ru.spbau.mit.kirakosian.Function2;

public class Const<U, V> extends Function2<U, V, U> {

    @Override
    public U apply(final U u, final V v) {
        return u;
    }
}
