package ru.spbau.mit.kirakosian.simple_functions;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kirakosian.Predicate;

public class IsZero extends Predicate<Integer> {

    @NotNull
    @Override
    public Boolean apply(final Integer a) {
        return a == 0;
    }
}
