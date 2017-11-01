package ru.spbau.mit.kirakosian;

public interface Function1<R, V> {

    R apply(V value);

    default <T> Function1<T, V> compose(final Function1<T, ? super R> g) {
        return (V value) -> g.apply(apply(value));
    }
}
