package ru.spbau.mit.kirakosian;

public interface Function2<R, V, U> {

    R apply(V v, U u);

    default <T> Function2<T, V, U> compose(final Function1<T, ? super R> g) {
        return (V v, U u) -> g.apply(apply(v, u));
    }

    default Function1<R, U> bind1(final V v) {
        return (U u) -> apply(v, u);
    }

    default Function1<R, V> bind2(final U u) {
        return (V v) -> apply(v, u);
    }

    default Function1<Function1<R, U>, V> curry() {
        return this::bind1;
    }
}
