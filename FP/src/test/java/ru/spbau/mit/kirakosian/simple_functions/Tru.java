package ru.spbau.mit.kirakosian.simple_functions;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kirakosian.Predicate;

public class Tru<V> extends Predicate<V> {

    @NotNull
    @Override
    public Boolean apply(final V v) {
        return true;
    }
}
