package ru.spbau.mit.kirakosian.simple_functions;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kirakosian.Function2;

import java.util.LinkedList;

public class BackConcat<T> extends Function2<T, LinkedList<? super T>, LinkedList<? super T>> {


    @NotNull
    @Override
    public LinkedList<? super T> apply(final T t, @NotNull final LinkedList<? super T> ts) {
        ts.add(t);
        return ts;
    }
}
