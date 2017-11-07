package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

/**
 * The class supports simple operations with two-argument functions.
 *
 * Class is abstract, thus you must inherit your class from this one and overwrite "apply" method.
 *
 * @param <V> type of the first argument
 * @param <U> type of the second argument
 * @param <R> type of the return value
 */
@SuppressWarnings("WeakerAccess")
public abstract class Function2<V, U, R> {

    /**
     * The method applies function to the given applicants.
     *
     * @param v first applicant
     * @param u second applicant
     * @return value of the evaluation
     */
    public abstract R apply(V v, U u);

    /**
     * The method creates new function by the composition of initial function and argument function.
     * <p>
     * New function will take two argument and consequently apply initial function and argument function to them.
     * <p>
     * Function will return g(f(x, y)), if f -- initial function and g -- argument function.
     * <p>
     * Therefore argument function must take exactly one argument of "super R" type, where R is a return type of the initial function.
     * In other words, it must be possible to apply argument function to any value returned by the initial function
     * but function may change the return value type.
     * <p>
     * The method cannot be overwritten.
     *
     * @param g function to compose with
     * @param <T> type of the "g" return value
     * @return new function equal to composition of two initial functions
     */
    @NotNull
    public final <T> Function2<V, U, T> compose(@NotNull final Function1<? super R, T> g) {
        return new Function2<V, U, T>() {
            @Override
            public T apply(final V v, final U u) {
                return g.apply(Function2.this.apply(v, u));
            }
        };
    }

    /**
     * Binds argument value to the first argument of the function.
     * <p>
     * The method returns new one-argument function constructed by binding the first argument
     * of the initial function with the given value.
     * <p>
     * For example, if function was f(x, y) and argument was v the new function will be g(x) = f(v, x).
     * <p>
     * Type of the argument and return value type will not be affected.
     * <p>
     * Cannot be overwritten.
     *
     * @param v value to bind
     * @return new function constructed by binding the first argument of the initial one
     */
    @NotNull
    public final Function1<U, R> bind1(final V v) {
        return new Function1<U, R>() {
            @Override
            public R apply(final U u) {
                return Function2.this.apply(v, u);
            }
        };
    }

    /**
     * Binds argument value to the second argument of the function.
     * <p>
     * The method returns new one-argument function constructed by binding the second argument
     * of the initial function with the given value.
     * <p>
     * For example, if function was f(x, y) and argument was u the new function will be g(x) = f(x, u).
     * <p>
     * Type of the argument and return value type will not be affected.
     * <p>
     * Cannot be overwritten.
     *
     * @param u value to bind
     * @return new function constructed by binding the second argument of the initial one
     */
    @NotNull
    public final Function1<V, R> bind2(final U u) {
        return new Function1<V, R>() {
            @Override
            public R apply(final V v) {
                return Function2.this.apply(v, u);
            }
        };
    }

    /**
     * The method transforms two-argument function to the consequence of two functions.
     *
     * In other words instead of taking two arguments at the same time returned function will
     * take them consequently.
     *
     * For example if f -- initial function and g -- returned function, f.apply(x, y) == g.apply(x).apply(y) for any x and y.
     *
     * @return new function which takes arguments consequently.
     */
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
