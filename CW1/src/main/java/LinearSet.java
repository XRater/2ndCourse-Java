import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LinearSet<T> extends AbstractSet<T> {

    private final LinkedHashMap.LinearIterator iterator;
    private final int size;

    public LinearSet(final LinkedHashMap.LinearIterator iterator, final int size) {
        this.iterator = iterator;
        this.size = size;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    @Override
    public int size() {
        return size;
    }

}
