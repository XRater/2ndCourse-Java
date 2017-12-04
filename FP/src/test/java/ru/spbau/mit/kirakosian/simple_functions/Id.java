package ru.spbau.mit.kirakosian.simple_functions;

import ru.spbau.mit.kirakosian.Function1;

public class Id<T> extends Function1<T, T> {

   @Override
    public T apply(final T v) {
        return v;
    }

}
