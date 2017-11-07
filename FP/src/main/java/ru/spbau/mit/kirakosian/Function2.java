package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public abstract class Function2<V, U, R> {

    public abstract R apply(V v, U u);

    @NotNull
    public final <T> Function2<V, U, T> compose(@NotNull final Function1<? super R, T> g) {
        return new Function2<V, U, T>() {
            @Override
            public T apply(final V v, final U u) {
                return g.apply(Function2.this.apply(v, u));
            }
        };
    }

    @NotNull
    public final Function1<U, R> bind1(final V v) {
        return new Function1<U, R>() {
            @Override
            public R apply(final U u) {
                return Function2.this.apply(v, u);
            }
        };
    }

    @NotNull
    public final Function1<V, R> bind2(final U u) {
        return new Function1<V, R>() {
            @Override
            public R apply(final V v) {
                return Function2.this.apply(v, u);
            }
        };
    }

    public final Function1<V, Function1<U, R>> curry() {
        return new Function1<V, Function1<U, R>>() {
            @NotNull
            @Override
            public Function1<U, R> apply(final V v) {
                return bind1(v);
            }
        };
    }
}
