package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public abstract class Function1<V, R> {

    public abstract R apply(V v);

    public final <T> Function1<V, T> compose(final Function1<? super R, T> g) {
        return new Function1<V, T>() {
            @Override
            public T apply(final V v) {
                return g.apply(Function1.this.apply(v));
            }
        };
    }
}
