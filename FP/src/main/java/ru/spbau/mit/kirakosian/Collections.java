package ru.spbau.mit.kirakosian;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public final class Collections {

    public static <R, E> List<R> map(final Function1<? super E, ? extends R> f, final Collection<E> c) {
        final List<R> list = new LinkedList<>();

        for (final E e : c) {
            list.add(f.apply(e));
        }

        return list;
    }

    public static <E> List<E> filter(final Predicate<? super E> p, final Collection<E> c) {
        final List<E> list = new LinkedList<>();

        for (final E e : c) {
            if (p.apply(e))  {
                list.add(e);
            }
        }

        return list;
    }

    public static <E> List<E> takeWhile(final Predicate<? super E> p, final Collection<E> c) {
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

    public static <E> List<E> takeUnless(final Predicate<? super E> p, final Collection<E> c) {
        return takeWhile(p.not(), c);
    }

    public static <E, R> R foldl(final Function2<? super R, ? super E, ? extends R> f, final Collection<E> c, R ini) {
        for (final E e : c) {
            ini = f.apply(ini, e);
        }

        return ini;
    }

    public static <E, R> R foldr(final Function2<? super E, ? super R, ? extends R> f, final Collection<E> c, R ini) {
        return foldrRec(f, c.iterator(), ini);
    }

    private static <E, R> R foldrRec(final Function2<? super E, ? super R, ? extends R> f, final Iterator<E> iter, R acc) {
        if (iter.hasNext())
            return f.apply(iter.next(), foldrRec(f, iter, acc));
        return acc;
    }
}

