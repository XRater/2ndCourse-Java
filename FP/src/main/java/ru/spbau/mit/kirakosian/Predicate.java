package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

/**
 * The class supports simple operations for predicates.
 * <p>
 * The class is abstract, thus you have to inherit from this class and overwrite "apply method".
 *
 * @param <V> type of the input value
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class Predicate<V> extends Function1<V, Boolean> {

    /**
     * Constant predicate that returns true for any input.
     */
    public final static Predicate<Object> ALWAYS_TRUE = new Predicate<Object>() {
        @Override
        public Boolean apply(final Object o) {
            return Boolean.TRUE;
        }
    };

    /**
     * Constant predicate that returns false for any input.
     */
    public final static Predicate<Object> ALWAYS_FALSE = new Predicate<Object>() {
        @Override
        public Boolean apply(final Object o) {
            return Boolean.FALSE;
        }
    };

    /**
     * Returns new predicate that consequently checks both of initial (this and argument) predicates
     * and returns true if at least one was true.
     * <p>
     * If the first one of predicates returns true the second one will not be evaluated.
     *
     * @param p second predicate to check
     * @return true if at least one of predicates is true and false otherwise
     */
    public final Predicate<V> or(@NotNull final Predicate<? super V> p) {
        return new Predicate<V>() {
            @NotNull
            @Override
            public Boolean apply(final V v) {
                return Predicate.this.apply(v) || p.apply(v);
            }
        };
    }

    /**
     * Returns new predicate that consequently checks both of initial (this and argument) predicates
     * and returns true if both of them were true.
     * <p>
     * If the first one of predicates returns false the second one will not be evaluated.
     *
     * @param p second predicate to check
     * @return true if both of predicates returns true and false otherwise
     */
    public final Predicate<V> and(@NotNull final Predicate<? super V> p) {
        return new Predicate<V>() {
            @NotNull
            @Override
            public Boolean apply(final V v) {
                return Predicate.this.apply(v) && p.apply(v);
            }
        };
    }

    /**
     * Returns new predicate equals to not(this).
     */
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
