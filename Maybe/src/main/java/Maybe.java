import exceptions.NoElementInMaybeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * @param <T> type to store
 */
public class Maybe<T> {

    @Nullable
    private final T value;
    private final boolean empty;

    public static <T> Maybe<T> just(@Nullable T t) {
        return new Maybe<>(t);
    }

    public static <T> Maybe<T> nothing() {
        return new Maybe<>();
    }

    private Maybe() {
        value = null;
        empty = true;
    }

    private Maybe(@Nullable T t) {
        value = t;
        empty = false;
    }

    @Nullable
    public T get() throws NoElementInMaybeException {
        if (empty) {
            throw new NoElementInMaybeException();
        }
        return value;
    }

    public boolean isPresent() {
        return !empty;
    }

    @NotNull
    public <U> Maybe<U> map(
            @NotNull Function<? super T, U> mapper) {
        if (empty) {
            return new Maybe<>();
        }
        return new Maybe<>(mapper.apply(value));
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Maybe<?>) {
            Maybe<?> om = (Maybe<?>) o;
            if (empty) {
                return om.empty;
            }
            return value == null ? om.value == null : value.equals(om.value);
        }
        return false;
    }
}
