package ru.spbau.mit.kirakosian;

/**
 * Basic list implementation. Used in StringHashMap.
 *
 * This list stores pairs of keys and values. The keys may not be unique.
 * Each key corresponds its own value.
 */
public class KeyValueStringList {
    private Node head = new Node();

    private int size;

    /**
     * Returns size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if list is empty
     * @return true if list is empty and false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     */
    public void clear() {
        size = 0;
        head.next = head;
        head.prev = head;
    }

    /**
     * Returns first key of the front element of the list
     * @return
     * front key value, null if list is empty
     */
    public String frontKey() {
        return head.next.key;
    }

    /**
     * Returns value of the front element of the list
     * @return
     * front value value, null if list is empty
     */
    public String frontValue() {
        return head.next.value;
    }

    /**
     * This method adds key and value pair to the end of the list.
     * If key equals to null this method does nothing
     * @param key
     * key to add
     * @param value
     * value to add
     */
    public void add(String key, String value) {
        if (key == null)
            throw new IllegalArgumentException();

        Node newNode = new Node(key, value);

        newNode.next = head;
        newNode.prev = head.prev;

        head.prev.next = newNode;
        head.prev = newNode;

        size++;
    }


    /**
     * This method changes the value found by the given key.
     * If list contains more then one element with the given key,
     * the method replaces the value of first found pair.
     * If list does not contain the given key,
     * this method adds new pair to the end of the list
     * @param key
     * key to find
     * @param value
     * value to set
     * @return replaced value and null if key was not found
     */
    public String replace(String key, String value) {
        if (key == null)
            return null;
        Node curr = head;
        while (curr.next != head) {
            curr = curr.next;
            if (curr.key.equals(key)) {
                String returnVal = curr.value;
                curr.value = value;
                return returnVal;
            }
        }

        add(key, value);
        return null;
    }

    /**
     * This method removes pair from the list by the given key.
     * If list contains more than one element with the given key,
     * the method removes the first found one.
     * @param key
     * key to remove
     * @return
     * value of removed element and null if the key was not found
     *
     */
    public String remove(String key) {
        Node curr = head;
        while (curr.next != head) {
            curr = curr.next;

            if (curr.key.equals(key)) {
                Node nxt = curr.next;
                curr.next.prev = curr.prev;
                curr.prev.next = nxt;
                size--;

                return curr.value;
            }

        }

        return null;
    }

    /**
     * This method finds value by the given key.
     * If list contains more then one element with the given key,
     * the method returns the first found value.
     * Always returns null if the key equals to null.
     * @param key
     * key to find
     * @return
     * value of found element and null if the key was not found
     */
    public String get(String key) {
        if (key == null)
            return null;
        Node curr = head;
        while (curr.next != head) {
            curr = curr.next;
            if (curr.key.equals(key))
                return curr.value;
        }
        return null;
    }

    /**
     * This method checks if the key is already stored in the list.
     * @param key
     * key to find
     * @return
     * true if the key was found in the list and false otherwise
     * returns false if the key equals to null
     */
    public boolean contains(String key) {
        if (key == null)
            return false;

        Node curr = head;
        while (curr.next != head) {
            curr = curr.next;
            if (curr.key.equals(key))
                return true;
        }
        return false;
    }

    /**
     * Outputs content of the list in form of string.
     * Returns pairs of keys and values in format "key: value"
     * For example, list with two pairs (key1, value1) and (key2, value2) returns {key1: value1, key2: value2}
     * @return string representation of the list
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

        private final String key;
        private String value;

        /**
         * Basic constructor. Sets links to itself.
         * Used for head node construction.
        */
        Node() {
            key = null;
            next = this;
            prev = this;
        }

        /**
         * Constructs Node from key and value arguments.
         * Sets prev and next links to null.
         * Used for regular list nodes.
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
