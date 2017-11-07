package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public final class Collections {

    @NotNull
    public static <R, E> List<R> map(@NotNull final Function1<? super E, ? extends R> f, @NotNull final Collection<E> c) {
        final List<R> list = new LinkedList<>();

        for (final E e : c) {
            list.add(f.apply(e));
        }

        return list;
    }

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

    @NotNull
    public static <E> List<E> takeUnless(@NotNull final Predicate<? super E> p, @NotNull final Collection<E> c) {
        return takeWhile(p.not(), c);
    }

    public static <E, R> R foldl(@NotNull final Function2<? super R, ? super E, ? extends R> f, @NotNull final Collection<E> c, R ini) {
        for (final E e : c) {
            ini = f.apply(ini, e);
        }

        return ini;
    }

    public static <E, R> R foldr(@NotNull final Function2<? super E, ? super R, ? extends R> f, @NotNull final Collection<E> c, final R ini) {
        return foldrRec(f, c.iterator(), ini);
    }

    private static <E, R> R foldrRec(@NotNull final Function2<? super E, ? super R, ? extends R> f, @NotNull final Iterator<E> iter, final R acc) {
        if (iter.hasNext())
            return f.apply(iter.next(), foldrRec(f, iter, acc));
        return acc;
    }
}

