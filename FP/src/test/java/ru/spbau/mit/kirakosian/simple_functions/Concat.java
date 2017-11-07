package ru.spbau.mit.kirakosian.simple_functions;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Concat<T> extends ru.spbau.mit.kirakosian.Function2<LinkedList<? super T>, T, LinkedList<? super T>> {

    @NotNull
    @Override
    public LinkedList<? super T> apply(@NotNull final LinkedList<? super T> ts, final T t) {
        ts.add(t);
        return ts;
    }
}
