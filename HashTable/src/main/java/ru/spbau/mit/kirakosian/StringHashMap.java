package java.ru.spbau.mit.kirakosian;

import java.util.function.Function;

/**
 * Basic hashmap implementation. This structure supports add remove and get operations.
 * Every operation takes constant time. It is also possible to provide hash-function by yourself.
 */
public class StringHashMap {

    private static final int INITIAL_SIZE = 1000;

    private int size;
    private Function<String, Integer> hashFunction = String::hashCode;
    private KeyValueStringList[] data = new KeyValueStringList[INITIAL_SIZE];

    /**
     * Constructs default HashMap with default hash function (String.hashCode()).
     * Initially creates 1000
     */
    StringHashMap() {}


    /**
     * Constructs hashmap with the given hash function. Hash function must take String as argument
     * and return any integer value. Also value of hash function must be same for equal strings.
     * @param f
     * hash function
     */
    StringHashMap(Function<String, Integer> f) {
        hashFunction = f;
    }

    public int size() {
        return size;
    }

    /**
     * Checks if hashmap is empty
     * @return true if hashmap is empty and false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Clears the table.
     */
    public void clear() {
        data = new KeyValueStringList[data.length];
        size = 0;
    }

    /**
     * This method adds key and value pair into hashtable.
     * If the key is equal to null pair will not be added.
     * All keys in hashmap must be unique, therefore if
     * the key already was in hashtable its value will be replaced
     * with the new one
     * @param key
     * key to add
     * @param value
     * value to add
     * @return
     * replaced value and null if the key was the new one
     */
    public String put(String key, String value) {
        if (key == null)
            return null;

        int index = hash(key);
        if (data[index] == null) {
            data[index] = new KeyValueStringList();
        }

        int oldSize = data[index].size();
        String returnVal = data[index].replace(key, value);
        if (oldSize != data[index].size())
            increaseSize();

        return returnVal;
    }

    /**
     * This method finds value by the given key.
     * Always returns null if the key equals to null.
     * @param key
     * key to find
     * @return
     * value of found element and null if element was not found
     */
    public String get(String key) {
        if (key == null)
            return null;

        int index = hash(key);
        if (data[index] == null)
            return null;

        return data[index].get(key);
    }


    /**
     * This method checks if there is already a value stored with the given key in hashmap.
     * null key is never stored.
     * @param key
     * key to find
     * @return
     * true if there a pair with the given key and false otherwise
     * returns false if key is equal to null
     */
    public boolean contains(String key) {
        if (key == null)
            return false;

        int index = hash(key);
        if (data[index] == null)
            return false;

        return data[index].contains(key);
    }

    /**
     * This method removes pair with the given key from the hashmap.
     * @param key
     * key to remove
     * @return
     * value of removed pair and null if the key was not found
     */
    public String remove(String key) {
        if (key == null)
            return null;

        int index = hash(key);
        if (data[index] == null)
            return null;

        int oldSize = data[index].size();
        String returnVal = data[index].remove(key);
        if (oldSize != data[index].size())
            decreaseSize();

        return returnVal;
    }

    private int hash(String key) {
        return (hashFunction.apply(key) & 0x7fffffff) % data.length;
    }

    private void decreaseSize() {
        size--;
    }

    private void increaseSize() {
        size++;
        if (size > data.length)
            rebuild(size * 2);
    }

    private void rebuild(int sz) {
        KeyValueStringList[] oldData = data;
        data = new KeyValueStringList[sz];
        size = 0;

        for (KeyValueStringList list : oldData) {
            if (list == null)
                continue;
            while (!list.isEmpty()) {
                put(list.frontKey(), list.frontValue());
                list.remove(list.frontKey());
            }
        }
    }
}
