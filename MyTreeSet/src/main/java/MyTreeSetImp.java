import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MyTreeSetImp<E> extends AbstractSet<E> implements MyTreeSet<E> {
    @Nullable
    private final Node root = new Node(null);

    private int size;
    private final Comparator<E> cmp;

    /**
     * Constructs tree. Stored elements must implement Comparable interface.
     */
    public MyTreeSetImp() {
        cmp = (o1, o2) -> {
            try {
                //noinspection unchecked
                return ((Comparable<E>) o1).compareTo(o2);
            } catch (@NotNull final ClassCastException e) {
                throw new IllegalArgumentException();
            }
        };
    }

    /**
     * Constructs tree with the given comparator for the elements.
     *
     * @param cmp comparator to use
     */
    public MyTreeSetImp(final Comparator<E> cmp) {
        this.cmp = cmp;
    }

    /**
     * The method constructs new view on the set, where elements are stored in descending order. Any changes in base or descending set
     * will affect both implementations.
     *
     * @return new set with descending order of elements.
     */
    @NotNull
    @Override
    public MyTreeSet<E> descendingSet() {
        return new ReversedSet(this);
    }

    /**
     * The method returns new iterator that visits set elements in descending order.
     *
     * @return new descending iterator.
     */
    @NotNull
    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingTreeIterator();
    }

    /**
     * The method returns new iterator. Iterator will visit elements in ascending order.
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new AscendingIterator();
    }

    /**
     * The method returns first element of the set. In terms of the comparator the smallest one.
     */
    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        assert root != null;
        return root.min().value;
    }

    /**
     * The method returns last element of the set. In terms of the comparator the largest one.
     */
    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        assert root != null;
        return root.max().value;
    }

    /**
     * Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
     * @param e the value to match
     */
    @Nullable
    @Override
    public E lower(final E e) {
        Node curr = root;
        assert curr != null;
        while (!curr.isLeaf()) {
            if (cmp.compare(e, curr.value) > 0) {
                if (curr.r.isLeaf()) {
                    return curr.value;
                } else {
                    curr = curr.r;
                }
            } else {
                if (curr.l.isLeaf()) {
                    while (curr.p != null && curr.isLeftSon()) {
                        curr = curr.p;
                    }
                    return curr.p == null ? null : curr.p.value;
                } else {
                    curr = curr.l;
                }
            }
        }
        return null;
    }

    /**
     * Returns the greatest element in this set strictly less than the given element, or null if there is no such element.
     * @param e the value to match
     */
    @Nullable
    @Override
    public E floor(final E e) {
        Node curr = root;
        assert curr != null;
        while (!curr.isLeaf()) {
            if (cmp.compare(e, curr.value) > 0) {
                if (curr.r.isLeaf()) {
                    return curr.value;
                } else {
                    curr = curr.r;
                }
            } else if (cmp.compare(e, curr.value) < 0) {
                if (curr.l.isLeaf()) {
                    while (curr.p != null && curr.isLeftSon()) {
                        curr = curr.p;
                    }
                    return curr.p == null ? null : curr.p.value;
                } else {
                    curr = curr.l;
                }
            } else {
                return curr.value;
            }
        }
        return null;
    }

    /**
     * Returns the least element in this set greater than or equal to the given element, or null if there is no such element.
     * @param e the value to match
     */
    @Nullable
    @Override
    public E ceiling(final E e) {
        Node curr = root;
        assert curr != null;
        while (!curr.isLeaf()) {
            if (cmp.compare(e, curr.value) < 0) {
                if (curr.l.isLeaf()) {
                    return curr.value;
                } else {
                    curr = curr.l;
                }
            } else if (cmp.compare(e, curr.value) > 0) {
                if (curr.r.isLeaf()) {
                    while (curr.p != null && curr.isRightSon()) {
                        curr = curr.p;
                    }
                    return curr.p == null ? null : curr.p.value;
                } else {
                    curr = curr.r;
                }
            } else {
                return curr.value;
            }
        }
        return null;
    }

    /**
     * Returns the least element in this set strictly greater than the given element, or null if there is no such element.
     * @param e the value to match
     */
    @Nullable
    @Override
    public E higher(final E e) {
        Node curr = root;
        assert curr != null;
        while (!curr.isLeaf()) {
            if (cmp.compare(e, curr.value) < 0) {
                if (curr.l.isLeaf()) {
                    return curr.value;
                } else {
                    curr = curr.l;
                }
            } else {
                if (curr.r.isLeaf()) {
                    while (curr.p != null && curr.isRightSon()) {
                        curr = curr.p;
                    }
                    return curr.p == null ? null : curr.p.value;
                } else {
                    curr = curr.r;
                }
            }
        }
        return null;
    }

    /**
     * The method returns size of the tree.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the tree is empty.
     * @return true if the tree was empty and false otherwise
     */
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
        assert curr != null;
        while (!curr.isLeaf()) {
            if (cmp.compare(value, curr.value) == 0) {
                return curr;
            }
            curr = cmp.compare(value, curr.value) > 0 ? curr.r : curr.l;
        }
        return curr;
    }

    /**
     * Returns string representation of the Tree
     */
    @NotNull
    @Override
    public String toString() {
        assert root != null;
        return stringValue(root);
    }

    private String stringValue(@NotNull final Node root) {
        if (root.value == null) {
            return "null";
        }
        return "<" + stringValue(root.l) + ">" + root.value + "<" + stringValue(root.r) + ">";
    }

    private class AscendingIterator implements Iterator<E> {
        @Nullable Node node = root;

        private AscendingIterator() {
            assert root != null;
            node = root.min();
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            if (node == null) {
                throw new NoSuchElementException();
            }
            final E value = node.value;
            node = node.next();
            return value;
        }
    }

    private class DescendingTreeIterator implements Iterator<E> {

        @Nullable Node node = root;

        private DescendingTreeIterator() {
            assert root != null;
            node = root.max();
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            if (node == null) {
                throw new NoSuchElementException();
            }
            final E value = node.value;
            node = node.prev();
            return value;
        }
    }

    private class ReversedSet extends AbstractSet<E> implements MyTreeSet<E> {

        private final MyTreeSet<E> origin;
        private ReversedSet(final MyTreeSet<E> set) {
            origin = set;
        }

        @NotNull
        @Override
        public Iterator<E> descendingIterator() {
            return origin.iterator();
        }

        @Override
        public MyTreeSet<E> descendingSet() {
            return origin.descendingSet();
        }

        @Override
        public E first() {
            return origin.last();
        }

        @Override
        public E last() {
            return origin.first();
        }

        @Nullable
        @Override
        public E lower(final E e) {
            return origin.higher(e);
        }

        @Nullable
        @Override
        public E floor(final E e) {
            return origin.ceiling(e);
        }

        @Nullable
        @Override
        public E ceiling(final E e) {
            return origin.floor(e);
        }

        @Nullable
        @Override
        public E higher(final E e) {
            return origin.lower(e);
        }

        @NotNull
        @Override
        public Iterator<E> iterator() {
            return origin.descendingIterator();
        }

        @Override
        public int size() {
            return origin.size();
        }
    }

    private class Node {
        private E value;
        private Node l;
        private Node r;

        private final Node p;

        public Node(final Node p) {
            this.p = p;
        }

        private void setValue(final E value) {
            this.value = value;
            if (l == null) {
                l = new Node(this);
            }
            if (r == null) {
                r = new Node(this);
            }
        }

        private boolean isLeaf() {
            return (l == null) && (r == null);
        }

        private boolean isRoot() {
            return p == null;
        }

        private boolean isRightSon() {
            return p != null && p.r == this;
        }

        private boolean isLeftSon() {
            return p != null && p.l == this;
        }

        private Node next() {
            Node curr = this;
            if (!curr.r.isLeaf()) {
                return curr.r.min();
            }
            while (curr.isRightSon()) {
                curr = curr.p;
            }
            return curr.p;
        }

        private Node prev() {
            Node curr = this;
            if (!curr.l.isLeaf()) {
                return curr.l.max();
            }
            while (curr.isLeftSon()) {
                curr = curr.p;
            }
            return curr.p;
        }

        @NotNull
        private Node min() {
            Node curr = this;
            while (!curr.l.isLeaf()) {
                curr = curr.l;
            }
            return curr;
        }

        @NotNull
        private Node max() {
            Node curr = this;
            while (!curr.r.isLeaf()) {
                curr = curr.r;
            }
            return curr;
        }

    }
}

