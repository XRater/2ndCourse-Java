package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * This class provides some methods to work with functions and collections, such as map, filter, foldr etc
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class Collections {

    /**
     * The method applies function to every element of collection and returns list with evaluated values.
     *
     * @param f function to apply
     * @param c collection to apply for
     * @param <R> type of the return list elements
     * @param <E> type of elements in collection
     * @return list of evaluated values
     */
    @NotNull
    public static <R, E> List<R> map(@NotNull final Function1<? super E, ? extends R> f, @NotNull final Collection<E> c) {
        final List<R> list = new LinkedList<>();

        for (final E e : c) {
            list.add(f.apply(e));
        }

        return list;
    }

    /**
     * The method filters elements by the given predicate and returns list of suited elements.
     * <p>
     * Order of elements in the list will be corresponded
     *
     * The method does not change the type of the collection.
     *
     * @param p predicate to check
     * @param c collection to filter
     * @param <E> type of the elements in collection
     * @return list of suitable elements
     */
    @NotNull
    public static <E> List<E> filter(@NotNull final Predicate<? super E> p, @NotNull final Collection<E> c) {
        final List<E> list = new LinkedList<>();

        for (final E e : c) {
            if (p.apply(e))  {
                list.add(e);
            }
        }

        return list;
    }

    /**
     * The method checks every element of collection by the predicate and adds them to return list while predicate is true.
     *
     * @param p predicate to check
     * @param c collection to work with
     * @param <E> type of the elements in collection
     * @return list of the taken elements
     */
    @NotNull
    public static <E> List<E> takeWhile(@NotNull final Predicate<? super E> p, @NotNull final Collection<E> c) {
        final List<E> list = new LinkedList<>();

        for (final E e : c) {
            if (p.apply(e)) {
                list.add(e);
            } else {
                break;
            }
        }

        return list;
    }

    /**
     * The method checks every element of collection by the predicate and adds them to return list while predicate is false.
     *
     * @param p predicate to check
     * @param c collection to work with
     * @param <E> type of the elements in collection
     * @return list of the taken elements
     */
    @NotNull
    public static <E> List<E> takeUnless(@NotNull final Predicate<? super E> p, @NotNull final Collection<E> c) {
        return takeWhile(p.not(), c);
    }

    /**
     * Left-associative fold of a collection.
     * <p>
     * Consequently applies binary function to the elements of collection from the begin to the end
     * starting with the initial value.
     *
     * @param f function to apply
     * @param c collection to fold
     * @param ini initial value
     * @param <E> type of the elements in collection
     * @param <R> type of the return value
     * @return result of the list folding
     */
    // Seems that it is enough <R, ? super E, ? extends R> instead of <? super R, ? super E, ? extends R>
    @SuppressWarnings("SpellCheckingInspection")
    public static <E, R> R foldl(@NotNull final Function2<R, ? super E, ? extends R> f, @NotNull final Collection<E> c, R ini) {
        for (final E e : c) {
            ini = f.apply(ini, e);
        }

        return ini;
    }

    /**
     * Right-associative fold of a collection.
     * <p>
     * Consequently applies binary function to the elements of collection from the end to begin
     * starting with the initial value.
     *
     * @param f function to apply
     * @param c collection to fold
     * @param ini initial value
     * @param <E> type of the elements in collection
     * @param <R> type of the return value
     * @return result of the list folding
     */
    // Seems that it is enough <? super E, R, ? extends R> instead of <? super E, ? super R, ? extends R>
    public static <E, R> R foldr(@NotNull final Function2<? super E, R, ? extends R> f, @NotNull final Collection<E> c, final R ini) {
        return foldrRec(f, c.iterator(), ini);
    }

    private static <E, R> R foldrRec(@NotNull final Function2<? super E, ? super R, ? extends R> f, @SuppressWarnings
            ("SpellCheckingInspection") @NotNull final Iterator<E> iter, final R acc) {
        if (iter.hasNext())
            return f.apply(iter.next(), foldrRec(f, iter, acc));
        return acc;
    }
}

