package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public abstract class Predicate<V> extends Function1<V, Boolean> {

    public final static Predicate<Object> ALWAYS_TRUE = new Predicate<Object>() {
        @Override
        public Boolean apply(final Object o) {
            return Boolean.TRUE;
        }
    };

    public final static Predicate<Object> ALWAYS_FALSE = new Predicate<Object>() {
        @Override
        public Boolean apply(final Object o) {
            return Boolean.FALSE;
        }
    };

    public final Predicate<V> or(@NotNull final Predicate<? super V> p) {
        return new Predicate<V>() {
            @NotNull
            @Override
            public Boolean apply(final V v) {
                return Predicate.this.apply(v) || p.apply(v);
            }
        };
    }

    public final Predicate<V> and(@NotNull final Predicate<? super V> p) {
        return new Predicate<V>() {
            @NotNull
            @Override
            public Boolean apply(final V v) {
                return Predicate.this.apply(v) && p.apply(v);
            }
        };
    }

    public final Predicate<V> not() {
        return new Predicate<V>() {
            @NotNull
            @Override
            public Boolean apply(final V v) {
                return !Predicate.this.apply(v);
            }
        };
    }
}
