package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

/**
 * This structure supports add, contains and size operations.
 * <p>
 * The structure represents search binary tree, where the left son is strictly less then its parent
 * and the right son is strictly greater then its parent.
 * <p>
 * ATTENTION! Tree may be unbalanced therefore worst time for every operation is O(size), but on the random data O
 * (log n) time is the average.
 *
 * @param <T> type to store in the set. Type must implement Comparable interface.
 */
@SuppressWarnings("WeakerAccess")
public class Set<T extends Comparable<T>> {

    @NotNull
    private final Node head = new Node();
    private int size;

    /**
     * The method checks if the set is empty.
     *
     * @return true if the set is empty and false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return number of elements in the set
     */
    public int size() {
        return size;
    }

    /**
     * The method adds value to the set. Value must be not null.
     *
     * @param value value to add
     * @return true if the value was the new one and false otherwise
     */
    public boolean add(@NotNull final T value) {
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
    public boolean contains(@NotNull final T value) {
        return !findNode(value).isLeaf();
    }

    /**
     * The method seeks for the right place to insert the value.
     *
     * @param value value to find
     * @return node with the value equal to the given one or the empty leaf
     */
    @NotNull
    private Node findNode(@NotNull final T value) {
        Node curr = head;
        while (!curr.isLeaf()) {
            if (value.compareTo(curr.value) == 0) {
                return curr;
            }
            curr = value.compareTo(curr.value) > 0 ? curr.r : curr.l;
        }
        return curr;
    }

    /**
     * Our invariant is: every Node either contains value and has both l and r not equal to null
     * either Node does not contain value and has both of the sons equal to null.
     */
    private class Node {
        private T value;
        private Node l;
        private Node r;

        private void setValue(final T value) {
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
