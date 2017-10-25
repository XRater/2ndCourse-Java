import org.jetbrains.annotations.NotNull;

import java.io.InvalidClassException;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

@SuppressWarnings("WeakerAccess")
public class MyTreeSetImp<E> extends AbstractSet<E> implements MyTreeSet<E> {

    private final Node root = new Node();
    private int size;
    private final Comparator<E> cmp;

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public MyTreeSet<E> descendingSet() {
        return null;
    }

    @Override
    public E first() {
        return null;
    }

    @Override
    public E last() {
        return null;
    }

    @Override
    public E lower(final E e) {
        return null;
    }

    @Override
    public E floor(final E e) {
        return null;
    }

    @Override
    public E ceiling(final E e) {
        return null;
    }

    @Override
    public E higher(final E e) {
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    public MyTreeSetImp() {
        cmp = (o1, o2) -> {
            try {
                //noinspection unchecked
                return ((Comparable<E>) o1).compareTo((E) o2);
            } catch (final ClassCastException e) {
                throw new IllegalArgumentException();
            }
        };
    }

    public MyTreeSetImp(final Comparator<E> cmp) {
        this.cmp = cmp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * The method adds value to the set. Value must be not null.
     *
     * @param value value to add
     * @return true if the value was the new one and false otherwise
     */
    public boolean add(@NotNull final E value) {
        final Node node = findNode(value);
        if (!node.isLeaf()) {
            return false;
        }
        node.setValue(value);
        size++;
        return true;
    }

    /**
     * The method checks if the set contains value.
     *
     * @param value value to find
     * @return true if there is an element equals to value and false otherwise
     */
    public boolean contains(@NotNull final Object value) {
        //noinspection unchecked
        return !findNode((E) value).isLeaf();
    }

    /**
     * The method seeks for the right place to insert the value.
     *
     * @param value value to find
     * @return node with the value equal to the given one or the empty leaf
     */
    @NotNull
    private Node findNode(@NotNull final E value) {
        Node curr = root;
        while (!curr.isLeaf()) {
            if (cmp.compare(value, curr.value) == 0) {
                return curr;
            }
            curr = cmp.compare(value, curr.value) > 0 ? curr.r : curr.l;
        }
        return curr;
    }

    private class Node {
        private E value;
        private Node l;
        private Node r;

        private void setValue(final E value) {
            this.value = value;
            if (l == null) {
                l = new Node();
            }
            if (r == null) {
                r = new Node();
            }
        }

        private boolean isLeaf() {
            return (l == null) && (r == null);
        }
    }
}
