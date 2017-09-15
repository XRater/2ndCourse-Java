package ru.spbau.mit.kirakosian;

/**
 * Basic implementation of list. Used in StringHashMap.
 *
 * This list stores pairs of keys and values. Different keys may be equal.
 * Key is used for deletion operations.
 */
public class KeyValueStringList {

    private Node head = new Node();
    private int size;


    /**
     * @return size of the list
     */
    public int size() {
        return size;
    }

    /**
     * @return true if list is empty and false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds passed as argument key and value pair to the end of the list.
     * If key equals null this method does nothing
     * @param key
     * key to add, key must be not null
     * @param value
     * value to add
     */
    public void add(String key, String value) {
        if (key == null)
            return;

        Node newNode = new Node(key, value);

        newNode.next = head;
        newNode.prev = head.prev;

        head.prev.next = newNode;
        head.prev = newNode;

        size++;
    }


    /**
     * @param key
     * key to find value
     * @return value of removed string and null if key was not found
     *
     * Extracts pair from the list by key. If list contains more then one element with required key, then extracts the first one.
     */
    public String remove(String key) {
        if (isEmpty())
            return null;

        Node curr = head.next;
        while (!curr.key.equals(key)) {
            curr = curr.next;
            if (curr == head)
                return null;
        }

        Node nxt = curr.next;
        curr.next.prev = curr.prev;
        curr.prev.next = nxt;
        size--;

        return curr.value;
    }


    /**
     * Simply clears the list. Constant speed.
     */
    public void clear() {
        size = 0;
        head.next = head;
        head.prev = head;
    }

    /**
     * Converts list to String.
     * Returns pairs of keys and values in format "key: value"
     * For example list with two pairs (key1, value1) and (key2, value2) returns {key1: value1, key2: value2}
     * @return readable variant of list
     */
    @Override
    public String toString() {
        if (isEmpty())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Node curr = head.next;
        sb.append(curr.key).append(": ").append(curr.value);

        while (curr.next != head) {
            curr = curr.next;
            sb.append(", ").append(curr.key).append(": ").append(curr.value);
        }

        sb.append('}');
        return sb.toString();
    }

    private static class Node {
        private Node next;
        private Node prev;

        private String key;
        private String value;

        /**
         * Basic constructor. Sets links to itself.
         * Used for head node construction.
         */
        Node() {
            next = this;
            prev = this;
        }

        /**
         * Constructs Node from passed key and value arguments.
         * Sets prev and next links to null.
         * Used for regular list nodes
         * @param key
         * key to store
         * @param value
         * value to store
         */
        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
