package ru.spbau.mit.kirakosian.simple_functions;

import ru.spbau.mit.kirakosian.Function1;

public class Id<T> implements Function1<T, T> {

   @Override
    public T apply(final T t) {
        return t;
    }

}
