import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A linear storage that supports element insertion and removal at
 * the end of the collection.
 *
 * This stack implementation using ArrayDeque inside.
 * @param <T> type of the stored value.
 */
@SuppressWarnings("WeakerAccess")
public class StackImp<T> implements Stack<T> {

    private final Deque<T> deque = new ArrayDeque<>();

    /**
     * {@link Stack#isEmpty()}
     */
    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    /**
     * {@link Stack#push(Object)}
     */
    @Override
    public void push(final T t) {
        deque.addLast(t);
    }

    /**
     * {@link Stack#top()}
     */
    @Override
    public T top() {
        return deque.getLast();
    }

    /**
     * {@link Stack#pop()}
     */
    @Override
    public T pop() {
        return deque.removeLast();
    }

    /**
     * {@link Stack#clear()}
     */
    @Override
    public void clear() {
        deque.clear();
    }
}
