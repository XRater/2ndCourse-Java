import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public interface MyTreeSet<E> extends Set<E> {

    /** {@link TreeSet#descendingIterator()} **/
    @NotNull Iterator<E> descendingIterator();

    /** {@link TreeSet#descendingSet()} **/
    MyTreeSet<E> descendingSet();


    /** {@link TreeSet#first()} **/
    E first();

    /** {@link TreeSet#last()} **/
    E last();


    /** {@link TreeSet#lower(E)} **/
    @Nullable E lower(E e);

    /** {@link TreeSet#floor(E)} **/
    @Nullable E floor(E e);


    /** {@link TreeSet#ceiling(E)} **/
    @Nullable E ceiling(E e);

    /** {@link TreeSet#higher(E)} **/
    @Nullable E higher(E e);
}