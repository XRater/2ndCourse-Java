package ru.spbau.mit.kirakosian.simple_functions;

import ru.spbau.mit.kirakosian.Function1;

public class Const42<V> implements Function1<Integer,V> {

    @Override
    public Integer apply(final V value) {
        return 42;
    }
}
