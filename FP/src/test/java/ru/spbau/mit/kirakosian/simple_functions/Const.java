package ru.spbau.mit.kirakosian.simple_functions;

import ru.spbau.mit.kirakosian.Function2;

public class Const<U, V> implements Function2<U, U, V> {

    @Override
    public U apply(final U u, final V v) {
        return u;
    }
}
