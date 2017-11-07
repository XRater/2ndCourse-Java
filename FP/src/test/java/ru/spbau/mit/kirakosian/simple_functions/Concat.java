package ru.spbau.mit.kirakosian.simple_functions;

import java.util.LinkedList;
import java.util.List;

public class Concat<T> extends ru.spbau.mit.kirakosian.Function2<LinkedList<? super T>, T, LinkedList<? super T>> {

    @Override
    public LinkedList<? super T> apply(final LinkedList<? super T> ts, final T t) {
        ts.add(t);
        return ts;
    }
}
