package ru.spbau.mit.kirakosian;


import org.jetbrains.annotations.NotNull;

/**
 * The class supports simple operations with one argument function.
 *
 * The class is abstract, thus you have to inherit from this class and overwrite "apply method".
 *
 * @param <V> type of the argument
 * @param <R> type of the return value
 */
@SuppressWarnings("WeakerAccess")
public abstract class Function1<V, R> {

    /**
     * The method applies function to the given value.
     * <p>
     * @param v applicant
     * @return result of the evaluation
     */
    public abstract R apply(V v);

    /**
     * The method creates new function as a composition of the initial one with the argument function.
     * <p>
     * If apply method called result function will consequently use apply methods of the initial function and argument function.
     * <p>
     * Function wil return g(f(x)), if f -- initial function and g -- argument function.
     * <p>
     * Therefore argument function must take as argument "super R" type, where R is a return type of the initial function.
     * In other words, it must be possible to apply argument function to any value returned by the initial function
     * but function may change the return value type.
     * <p>
     * The method cannot be overwritten.
     *
     * @param g function to compose with
     * @param <T> return type of the given function
     * @return new function equals to the composition of two initial functions
     */
    public final <T> Function1<V, T> compose(@NotNull final Function1<? super R, T> g) {
        return new Function1<V, T>() {
            @Override
            public T apply(final V v) {
                return g.apply(Function1.this.apply(v));
            }
        };
    }
}
