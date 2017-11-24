import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A linear storage that supports element insertion and removal at
 * the end of the collection.
 * @param <T> type of the stored value.
 */
@SuppressWarnings("WeakerAccess")
public class Stack<T> {

    private final Deque<T> deque = new ArrayDeque<>();

    /**
     * Checks if stack is empty.
     * @return true if stack is empty and false otherwise.
     */
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    /**
     * The method inserts element to the end of the stack.
     * <p>
     * Element must be not null. If it is not, NullPointerException will be thrown.
     * @param t value to insert.
     */
    public void push(final T t) {
        deque.addLast(t);
    }

    /**
     * Returns value of the top element of the stack.
     */
    public T top() {
        return deque.getLast();
    }

    /**
     * The method removes element from the end of the stack.
     * <p>
     * If extracted value was equal to null, NullPointerException will be thrown.
     * @return value of removed element.
     */
    public T pop() {
        return deque.removeLast();
    }
}
