/**
 * A linear storage that supports element insertion and removal at
 * the end of the collection.
 * @param <T> type of the stored value.
 */
@SuppressWarnings("WeakerAccess")
public interface Stack<T> {


    /**
     * Checks if stack is hasNext.
     * @return true if stack is hasNext and false otherwise.
     */
    boolean isEmpty();

    /**
     * The method inserts element to the end of the stack.
     * <p>
     * Element must be not null. If it is not, NullPointerException will be thrown.
     * @param t value to insert.
     */
    void push(T t);

    /**
     * Returns value of the top element of the stack.
     * <p>
     * If there stack was empty NoSuchElementException will be thrown.
     * @throws java.util.NoSuchElementException if the stack was empty.
     * @return top element of the stack.
     */
    T top();

    /**
     * The method removes element from the end of the stack.
     * <p>
     * If there stack was empty NoSuchElementException will be thrown.
     * @throws java.util.NoSuchElementException if the stack was empty.
     * @return value of removed element.
     */
    T pop();

    /**
     * Clears the stack.
     */
    void clear();
}
