import exceptions.NoElementInMaybeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * 
 * @param <T> type of the stored value.
 */
@SuppressWarnings("WeakerAccess")
public class Maybe<T> {

    @Nullable
    private final T value;
    private final boolean empty;

    /**
     * The method constructs present maybe object from the given value.
     *
     * @param t   value to store
     * @param <T> type of the stored value.
     * @return new Maybe constructed from the value
     */
    public static <T> Maybe<T> just(@Nullable final T t) {
        return new Maybe<>(t);
    }

    /**
     * return new empty maybe.
     */
    public static <T> Maybe<T> nothing() {
        return new Maybe<>();
    }

    private Maybe() {
        value = null;
        empty = true;
    }

    private Maybe(@Nullable final T t) {
        value = t;
        empty = false;
    }

    /**
     * Checks if the maybe object stores value. (maybe has Just type)
     *
     * @return true if maybe is Just and false otherwise
     */
    public boolean isPresent() {
        return !empty;
    }

    /**
     * The method returns value stored inside the Maybe object.
     *
     * @return stored int the Maybe value if there any.
     * @throws NoElementInMaybeException if Maybe object was not present
     */
    @Nullable
    public T get() throws NoElementInMaybeException {
        if (empty) {
            throw new NoElementInMaybeException();
        }
        return value;
    }

    /**
     * @param mapper function to apply to the value
     * @param <U>    return type of the given function
     * @return new Maybe object constructed from the old one by applying mapper to its value
     */
    @NotNull
    public <U> Maybe<U> map(
            @NotNull final Function<? super T, U> mapper) {
        if (empty) {
            return new Maybe<>();
        }
        return new Maybe<>(mapper.apply(value));
    }

    /**
     * Checks if Maybe object is equal to the given object.
     * If the object is not an instance of Maybe class returns false.
     * <p>
     * Returns true if both objects are empty or both elements are just and stores same objects (equals method for
     * them returns true)
     *
     * @param o object to compare
     * @return true if objects are equal and false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Maybe<?>) {
            final Maybe<?> om = (Maybe<?>) o;
            if (empty) {
                return om.empty;
            }
            return value == null ? om.value == null : value.equals(om.value);
        }
        return false;
    }
}
