import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LinkedHashMap<K, V> extends AbstractMap<K, V> {

    private LinearIterator iterator;
    private int size = 0;
    private MyEntry<K, V> last;

    private final ArrayList<ArrayList<MyEntry<K, V>>> data = new ArrayList<>();


    @Override
    public V put(final K k, final V v) {
        int index = hash(k);
        MyEntry<K, V> newEntry = new MyEntry<>(k , v);
        if (last == null) {
            iterator = new LinearIterator(newEntry);
        } else {
            last.next = newEntry;
        }
        last = newEntry;

        data.get(index).add(newEntry);
        return v;
    }

    @Override
    public V get(final Object o) {
        K key = (K) o;
        int index = hash(key);
        for (MyEntry<K, V> entry : data.get(index)) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(final Object o) {
        K key = (K) o;
        int index = hash(key);
        for (MyEntry<K, V> entry : data.get(index)) {
            if (entry.key == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        for (ArrayList<MyEntry<K, V>> list : data) {
            list.clear();
        }
    }

//    @Override
//    public V remove(final Object o) {
//        K key = (K) o;
//        int index = hash(key);
//
//        MyEntry<K, V>
//    }

    @Override
    public int size() {
        return size;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % data.size();
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new LinearSet<>(iterator, size);
    }

    public static class MyEntry<K, V> implements Entry<K, V> {

        private MyEntry<K, V> next;

        private MyEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        private K key;
        private V value;

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(final V v) {
            V oldValue = value;
            value = v;
            return oldValue;
        }

        public MyEntry<K, V> next() {
            return next;
        }
    }

    public class LinearIterator implements Iterator<MyEntry> {

        private MyEntry<K, V> value;

        private LinearIterator(final MyEntry<K, V> value) {
            this.value = value;
        }

        @Override
        public boolean hasNext() {
            return value.next() != null;
        }

        @Override
        public MyEntry<K, V> next() {
            MyEntry<K, V> lastValue = value;
            value = value.next();
            return lastValue;
        }
    }
}
