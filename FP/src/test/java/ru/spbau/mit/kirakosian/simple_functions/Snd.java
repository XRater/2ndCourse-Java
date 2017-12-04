package ru.spbau.mit.kirakosian.simple_functions;

import ru.spbau.mit.kirakosian.Function2;

public class Snd<U, V> extends Function2<U, V, V> {

    @Override
    public V apply(final U u, final V v) {
        return v;
    }
}
